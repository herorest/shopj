package com.boot.service.impl;

import com.boot.mapper.OrderItemsMapper;
import com.boot.mapper.OrderStatusMapper;
import com.boot.mapper.OrdersMapper;
import com.boot.mapper.UserAddressMapper;
import com.boot.pojo.*;
import com.boot.pojo.bo.AddressBo;
import com.boot.pojo.bo.SubmitOrderBo;
import com.boot.pojo.vo.MerchantOrdersVo;
import com.boot.pojo.vo.OrderVo;
import com.boot.service.AddressService;
import com.boot.service.ItemService;
import com.boot.service.OrderService;
import enums.OrderStatusEnum;
import enums.YesOrNo;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceTmpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVo createOrder(SubmitOrderBo submitOrderBo) {
        String userId = submitOrderBo.getUserId();
        String addressId = submitOrderBo.getAddressId();
        String itemSpecIds = submitOrderBo.getItemSpecIds();
        Integer payMethod = submitOrderBo.getPayMethod();
        String leftMsg = submitOrderBo.getLeftMsg();
        Integer postAmount = 0;

        String orderId = sid.nextShort();
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);

        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setUpdatedTime(new Date());
        newOrder.setCreatedTime(new Date());

        UserAddress userAddress = addressService.queryUserAddress(userId, addressId);
        if(userAddress == null){
            return null;
        }
        newOrder.setReceiverName(userAddress.getReceiver());
        newOrder.setReceiverMobile(userAddress.getMobile());
        newOrder.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + " " + userAddress.getDetail());

        String itemSpecIdArr[] = itemSpecIds.split(",");
        Integer totalAmount = 0;    //商品原价累计
        Integer realPayAmount = 0;  //优惠后的实际价格累计
        for(String itemSpecId: itemSpecIdArr){
            //TODO 改为redis缓存后再处理
            int buyCounts = 1;

            ItemsSpec itemSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemSpec.getPriceDiscount() * buyCounts;

            //获取信息及图片
            String itemId = itemSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            OrderItems subOrderItem = new OrderItems();
            String subOrderId = sid.nextShort();
            subOrderItem.setOrderId(orderId);
            subOrderItem.setId(subOrderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemSpec.getName());
            subOrderItem.setPrice(itemSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);


            //提交订单后，规格表中需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
        newOrder.setRealPayAmount(realPayAmount);
        newOrder.setTotalAmount(totalAmount);
        ordersMapper.insert(newOrder);

        //保存订单状态
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        //创建商户订单，传给支付中心
        MerchantOrdersVo merchantOrdersVo = new MerchantOrdersVo();
        merchantOrdersVo.setMerchantOrderId(orderId);
        merchantOrdersVo.setMerchantUserId(userId);
        merchantOrdersVo.setAmount(realPayAmount + postAmount);
        merchantOrdersVo.setPayMethod(payMethod);

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        orderVo.setMerchantOrdersVo(merchantOrdersVo);
        return orderVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }
}
