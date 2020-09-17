package com.boot.service;

import com.boot.pojo.OrderStatus;
import com.boot.pojo.UserAddress;
import com.boot.pojo.bo.AddressBo;
import com.boot.pojo.bo.SubmitOrderBo;
import com.boot.pojo.vo.OrderVo;

import java.util.List;

public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBo
     */
    public OrderVo createOrder(SubmitOrderBo submitOrderBo);

    /**
     * 更新订单状态
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    public OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    public void closeOrder();
}
