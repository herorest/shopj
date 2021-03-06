package com.boot.service.center;

import com.boot.pojo.Orders;
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

    /**
     * 订单状态 -> 商家发货
     */
    public void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId
     * @param orderId
     * @return
     */
    public Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 -> 确认收货
     * @return
     */
    public boolean updateReceiveOrderStatus(String orderId);

    /**
     * 逻辑删除订单
     * @param orderId
     * @return
     */
    public boolean deleteOrder(String userId, String orderId);
}
