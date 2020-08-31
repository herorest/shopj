package com.boot.service.impl;

import com.boot.mapper.UserAddressMapper;
import com.boot.pojo.UserAddress;
import com.boot.pojo.bo.AddressBo;
import com.boot.service.AddressService;
import enums.YesOrNo;
import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceTmpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress ua = new UserAddress();
        ua.setUserId(userId);
        return userAddressMapper.select(ua);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void addNewUserAddress(AddressBo addressBo) {
        //判断是否存在，如果没有，则新增为默认地址
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBo.getUserId());
        if(addressList == null || addressList.isEmpty() || addressList.size() == 0){
            isDefault = 1;
        }

        //保存
        String addId = sid.nextShort();
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBo, newAddress);
        newAddress.setId(addId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(newAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void updateUserAddress(AddressBo addressBo) {
        String addressId = addressBo.getAddressId();
        UserAddress updateAddress = new UserAddress();
        BeanUtils.copyProperties(addressBo, updateAddress);
        updateAddress.setId(addressId);
        updateAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(updateAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void delUserAddress(String userId, String addressId) {
        UserAddress delAddress = new UserAddress();
        delAddress.setId(addressId);
        delAddress.setUserId(userId);
        userAddressMapper.deleteByPrimaryKey(delAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void updateUserAddressToDefault(String userId, String addressId) {
        // 查找默认地址，设置为不默认
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        address.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list = userAddressMapper.select(address);
        for(UserAddress ua : list){
            ua.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }

        //设置指定id的地址为默认
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        return userAddressMapper.selectOne(userAddress);
    }
}
