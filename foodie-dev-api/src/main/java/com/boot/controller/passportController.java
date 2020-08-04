package com.boot.controller;

import com.boot.pojo.Users;
import com.boot.pojo.bo.UserBo;
import com.boot.service.StuService;
import com.boot.service.UserService;
import com.boot.utils.CookieUtils;
import com.boot.utils.JSONResult;
import com.boot.utils.JsonUtils;
import com.boot.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Controller
@Api(value="注册登陆", tags = {"用户登陆注册的接口"})
@RestController
@RequestMapping("passport")
public class passportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="用户是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username){
        // 不能为空
        if(StringUtils.isBlank(username)){
            return new JSONResult("用户名不能为空", 501);
        }

        // 查找是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if(!isExist){
            return new JSONResult("用户不存在", 501);
        }

        return new JSONResult("ok");
    }

    @ApiOperation(value="用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public JSONResult register(@RequestBody UserBo userBo, HttpServletRequest request, HttpServletResponse response){
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPwd = userBo.getConfirmPassword();

        if(
            StringUtils.isBlank(username) ||
            StringUtils.isBlank(password) ||
            StringUtils.isBlank(confirmPwd)
        ){
            return new JSONResult("用户名密码不能为空", 502);
        }

        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist){
            return new JSONResult("用户名已经存在", 502);
        }

        if(password.length() < 6){
            return new JSONResult("密码长度不能少于6", 502);
        }

        if(!password.equals(confirmPwd)){
            return new JSONResult("两次密码输入不一致", 502);
        }

        Users userResult = userService.createUser(userBo);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        return new JSONResult("ok");
    }

    @ApiOperation(value="用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBo userBo, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String username = userBo.getUsername();
        String password = userBo.getPassword();

        if(
            StringUtils.isBlank(username) ||
            StringUtils.isBlank(password)
        ){
            return new JSONResult("用户名密码不能为空", 503);
        }

        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if(userResult == null){
            return new JSONResult("用户名密码不正确", 503);
        }

        userResult = this.setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        return new JSONResult("ok");
    }

    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setSex(null);
        userResult.setMobile(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

}
