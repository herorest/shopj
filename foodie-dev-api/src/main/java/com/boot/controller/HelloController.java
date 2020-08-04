package com.boot.controller;

import com.boot.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Controller
@ApiIgnore
@RestController
public class HelloController {

    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/h")
    public Object hello(){

        try{
            logger.debug("debug");
            logger.info("info");
            logger.error("error");
            logger.warn("warn");

            String startStr = "2020.1.1 00:00:00";
            String endStr = "2020.12.31 23:59:59";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

            Date start = sdf.parse(startStr);
            Date end = sdf.parse(endStr);

            Integer i = DateUtil.daysBetween(start, end);

            System.out.println(i);

        }catch(Exception e){

        }


        return "hello world";
    }
}
