package com.boot.config;

import com.boot.service.OrderService;
import com.boot.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务，自动关闭订单
 */

@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    /**
     * 定时任务弊端
     * 1.时间差
     * 2.不支持集群
     * 3.数据库压力大
     *
     * 仅适用小型项目
     * 解决方案 MQ
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrder(){
        System.out.println("当前时间：" + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
        orderService.closeOrder();
    }
}
