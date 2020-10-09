package com.boot.service.impl.center;

import com.boot.mapper.OrdersMapperCustom;
import com.boot.service.center.MyOrdersService;
import com.boot.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyOrdersServiceTmpl implements MyOrdersService {

    @Autowired
    public OrdersMapperCustom ordersMapperCustom;

    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        return null;
    }
}
