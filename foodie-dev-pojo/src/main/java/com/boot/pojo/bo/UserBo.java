package com.boot.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 与业务相关联的实体类
 */
@ApiModel(value = "用户对象BO")
public class UserBo {

    @ApiModelProperty(value = "用户名", name = "username", example = "boot", required = true)
    private String username;

    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = false)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
