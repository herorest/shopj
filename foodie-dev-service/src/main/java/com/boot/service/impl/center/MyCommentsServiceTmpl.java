package com.boot.service.impl.center;

import com.boot.mapper.OrderStatusMapper;
import com.boot.mapper.OrdersMapper;
import com.boot.mapper.OrdersMapperCustom;
import com.boot.pojo.OrderItems;
import com.boot.pojo.OrderStatus;
import com.boot.pojo.Orders;
import com.boot.pojo.vo.MyOrdersVo;
import com.boot.service.MyCommentsService;
import com.boot.service.center.MyOrdersService;
import com.boot.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.OrderStatusEnum;
import enums.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyCommentsServiceTmpl implements MyCommentsService {

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return null;
    }
}
