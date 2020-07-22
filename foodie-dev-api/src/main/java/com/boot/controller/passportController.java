package com.boot.controller;

import com.boot.pojo.bo.UserBo;
import com.boot.service.StuService;
import com.boot.service.UserService;
import com.boot.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@RequestMapping("passport")
public class passportController {

    @Autowired
    private UserService userService;

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

    @PostMapping("/register")
    public JSONResult register(@RequestBody UserBo userBo){
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPwd = userBo.getConfirmPassword();

        if(
            StringUtils.isBlank(username) ||
            StringUtils.isBlank(password) ||
            StringUtils.isBlank(confirmPwd)
        ){
            return new JSONResult("用户名密码不能为空", 501);
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

        userService.createUser(userBo);

        return new JSONResult("ok");
    }

}
