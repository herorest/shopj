package com.boot.pojo.vo;

import java.util.List;

/**
 * 二级分类Vo
 * 提供给前端展示用
 */
public class MerchantOrdersVo {
    private String merchantOrderId; //商户订单号
    private String merchantUserId;  //商户方的发起用户的用户id
    private Integer amount;         //实际支付的总金额
    private Integer payMethod;      //支付方式 1微信，2支付宝
    private String returnUrl;       // 支付成功后回调

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
