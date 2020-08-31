package com.boot.service;

import com.boot.pojo.UserAddress;
import com.boot.pojo.bo.AddressBo;

import java.util.List;

public interface AddressService {

    /**
     * 获取用户收货地址列表
     */
    public List<UserAddress> queryAll(String userId);


    /**
     * 新增地址
     * @param addressBo
     */
    public void addNewUserAddress(AddressBo addressBo);

    /**
     * 更新地址
     * @param addressBo
     */
    public void updateUserAddress(AddressBo addressBo);

    /**
     * 删除地址
     * @param userId
     * @param addressId
     */
    public void delUserAddress(String userId, String addressId);

    /**
     * 设置默认地址
     * @param userId
     * @param addressId
     */
    public void updateUserAddressToDefault(String userId, String addressId);

    /**
     * 获取地址信息
     * @return
     */
    public UserAddress queryUserAddress(String userId, String addressId);

}
