package com.boot.controller;

import com.boot.pojo.OrderStatus;
import com.boot.pojo.bo.SubmitOrderBo;
import com.boot.pojo.vo.MerchantOrdersVo;
import com.boot.pojo.vo.OrderVo;
import com.boot.service.OrderService;
import com.boot.utils.JSONResult;
import enums.OrderStatusEnum;
import enums.PayMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关", tags={"订单相关的api"})
@RestController
@RequestMapping("address")
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "创建订单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBo submitOrderBo, HttpServletRequest request, HttpServletResponse response){
        if(submitOrderBo.getPayMethod() != PayMethod.WEIXIN.type && submitOrderBo.getPayMethod() != PayMethod.ALIPAY.type){
            return new JSONResult("支付方式不支持", 512);
        }

        //创建订单
        OrderVo orderVo = orderService.createOrder(submitOrderBo);
        String orderId = orderVo.getOrderId();
        MerchantOrdersVo merchantOrdersVo = orderVo.getMerchantOrdersVo();
        merchantOrdersVo.setReturnUrl(payReturnUrl);

        //先设成1分
        merchantOrdersVo.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("bootId", "boot");
        headers.add("password", "");
        HttpEntity<MerchantOrdersVo> entity = new HttpEntity<>(merchantOrdersVo, headers);
        ResponseEntity<JSONResult> responseEntity = restTemplate.postForEntity(paymentUrl, entity, JSONResult.class);
        JSONResult paymentResult = responseEntity.getBody();
        if(paymentResult.getState() != 200){
            return new JSONResult("订单失败", 512);
        }

        //TODO 移除购物车的商品

        //支付

        return new JSONResult(orderId);
    }

    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId){
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return new JSONResult(orderStatus);
    }
}
