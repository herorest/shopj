package com.boot.service;

import com.boot.pojo.UserAddress;
import com.boot.pojo.bo.AddressBo;
import com.boot.pojo.bo.SubmitOrderBo;

import java.util.List;

public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBo
     */
    public void createOrder(SubmitOrderBo submitOrderBo);
}
