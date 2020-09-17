package com.boot;

import com.boot.config.Swagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
// 扫描mybatis通用mapper所在的包
@MapperScan(basePackages = "com.boot.mapper")
// 扫描所有包及相关组件包
@ComponentScan(basePackages = {"com.boot", "org.n3r.idworker"})
//开启定时任务
//@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
