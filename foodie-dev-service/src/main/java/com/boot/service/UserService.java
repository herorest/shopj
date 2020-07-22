package com.boot.service;

import com.boot.pojo.Users;
import com.boot.pojo.bo.UserBo;

public interface UserService {
    /**
     * 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

    public Users createUser(UserBo userBO);

//    public Users queryUserForLogin(String username, String password);
}
