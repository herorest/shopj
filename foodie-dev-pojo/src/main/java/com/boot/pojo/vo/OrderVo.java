package com.boot.pojo.vo;

/**
 * 二级分类Vo
 * 提供给前端展示用
 */
public class OrderVo {
    private String orderId;
    private MerchantOrdersVo merchantOrdersVo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVo getMerchantOrdersVo() {
        return merchantOrdersVo;
    }

    public void setMerchantOrdersVo(MerchantOrdersVo merchantOrdersVo) {
        this.merchantOrdersVo = merchantOrdersVo;
    }
}
