package com.boot.controller;

import com.boot.pojo.UserAddress;
import com.boot.pojo.bo.AddressBo;
import com.boot.pojo.bo.SubmitOrderBo;
import com.boot.service.AddressService;
import com.boot.utils.JSONResult;
import com.boot.utils.MobileEmailUtils;
import enums.PayMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "订单相关", tags={"订单相关的api"})
@RestController
@RequestMapping("address")
public class OrdersController {

    @ApiOperation(value = "创建订单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBo submitOrderBo){
        if(submitOrderBo.getPayMethod() != PayMethod.WEIXIN.type && submitOrderBo.getPayMethod() != PayMethod.ALIPAY.type){
            return new JSONResult("支付方式不支持", 512);
        }
        return new JSONResult("ok");
    }
}
