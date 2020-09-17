package com.boot.service.center;

import com.boot.pojo.Users;
import com.boot.pojo.bo.UserBo;
import com.boot.pojo.bo.center.CenterUserBo;

public interface CenterUserService {
    /**
     * 根据用户id查询用户
     */
    public Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBo
     */
    public Users updateUserInfo(String userId, CenterUserBo centerUserBo);
}
