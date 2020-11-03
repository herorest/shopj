package com.boot.service;

import com.boot.pojo.OrderItems;
import com.boot.pojo.OrderStatus;
import com.boot.pojo.Users;
import com.boot.pojo.bo.SubmitOrderBo;
import com.boot.pojo.vo.OrderVo;

import java.util.List;

public interface MyCommentsService {

    public List<OrderItems> queryPendingComment(String orderId);

}
