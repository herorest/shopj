package com.boot.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    // 微信支付成功 - 支付中心 - 平台
    // 回调通知的url
    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    // 支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    // 头像上传地址
//    public static final String image_user_face_url =
//            File.separator + "Users" +
//            File.separator +"songjing"+
//            File.separator +"IdeaProjects" +
//            File.separator + "h3base" +
//            File.separator +"upload";
}
