package com.boot.service.center;

import com.boot.utils.PagedGridResult;

public interface MyOrdersService {
    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

}