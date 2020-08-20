package com.boot.controller;

import com.boot.pojo.bo.ShopcartBo;
import com.boot.utils.JSONResult;
import com.boot.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(value = "购物车接口controller", tags = {"购物车接口相关的api"})
@RestController
@RequestMapping("shopcart")
public class ShopcatController {

    final static Logger logger = LoggerFactory.getLogger(ShopcatController.class);

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @GetMapping("/add")
    public JSONResult add(@RequestParam String userId, @RequestBody ShopcartBo shopcartBo, HttpServletRequest request, HttpServletResponse response){
//        HttpSession session = request.getSession();
//        session.setAttribute("userInfo", "new user");
//        session.setMaxInactiveInterval(3600);
//        session.getAttribute("userInfo");

        if(StringUtils.isBlank(userId)){
            return new JSONResult("缺少用户", 505);
        }

        logger.info(JsonUtils.objectToJson(shopcartBo));

        //TODO redis记录购物车

        return new JSONResult("ok");
    }
}
