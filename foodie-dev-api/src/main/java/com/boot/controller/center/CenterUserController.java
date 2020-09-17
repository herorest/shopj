package com.boot.controller.center;

import com.boot.pojo.Users;
import com.boot.pojo.bo.center.CenterUserBo;
import com.boot.service.center.CenterUserService;
import com.boot.utils.CookieUtils;
import com.boot.utils.JSONResult;
import com.boot.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody CenterUserBo centerUserBo,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ){
        Users users = centerUserService.updateUserInfo(userId, centerUserBo);

        // TODO 后续修改为redis
        // 临时处理
        users = setNullProperty(users);
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, "user", JsonUtils.objectToJson(users), true);
        return new JSONResult();
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
