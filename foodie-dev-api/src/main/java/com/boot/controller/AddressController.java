package com.boot.controller;

import com.boot.pojo.UserAddress;
import com.boot.pojo.bo.AddressBo;
import com.boot.service.AddressService;
import com.boot.utils.JSONResult;
import com.boot.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "地址相关", tags={"地址相关的api"})
@RestController
@RequestMapping("address")
public class AddressController {
    /**
     * 查询用户的所有收货地址列表
     * 新增/删除/修改/设置收货地址
     */
    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id获取收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public JSONResult list(@RequestParam String userId){
        if (StringUtils.isBlank(userId)){
            return new JSONResult("用户不存在", 509);
        }
        List<UserAddress> list = addressService.queryAll(userId);
        return new JSONResult(list);
    }

    @ApiOperation(value = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestBody AddressBo addressBo){
        JSONResult checkRes = this.check(addressBo);
        if(checkRes.getErrcode() != 200){
            return checkRes;
        }
        addressService.addNewUserAddress(addressBo);
        return new JSONResult("ok");
    }

    @ApiOperation(value = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@RequestBody AddressBo addressBo){
        if(StringUtils.isBlank(addressBo.getAddressId())){
            return new JSONResult("地址id不能为空", 510);
        }

        JSONResult checkRes = this.check(addressBo);
        if(checkRes.getErrcode() != 200){
            return checkRes;
        }
        addressService.addNewUserAddress(addressBo);
        return new JSONResult("ok");
    }

    @ApiOperation(value = "用户修改地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(@RequestParam String userId, @RequestParam String addressId){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return new JSONResult("请选择地址", 511);
        }
        addressService.delUserAddress(userId, addressId);
        return new JSONResult("ok");
    }

    @ApiOperation(value = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefault")
    public JSONResult setDefault(@RequestParam String userId, @RequestParam String addressId){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return new JSONResult("请选择地址", 511);
        }
        addressService.updateUserAddressToDefault(userId, addressId);
        return new JSONResult("ok");
    }

    private JSONResult check(AddressBo addressBo){
        String receiver = addressBo.getReceiver();
        if (StringUtils.isBlank(receiver)){
            return new JSONResult("收货人不能为空", 510);
        }
        if(receiver.length() > 12){
            return new JSONResult("收货人姓名不能超过12个字符", 510);
        }
        String mobile = addressBo.getMobile();
        if (StringUtils.isBlank(mobile)){
            return new JSONResult("收货人手机号不能为空", 510);
        }
        if(mobile.length() != 11){
            return new JSONResult("手机号不正确", 510);
        }
        Boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if(!isMobileOk){
            return new JSONResult("收货人手机号格式不正确", 510);
        }
        String province = addressBo.getProvince();
        String city = addressBo.getCity();
        String district = addressBo.getDistrict();
        String detail = addressBo.getDetail();
        if (StringUtils.isBlank(province) || StringUtils.isBlank(city) || StringUtils.isBlank(district) || StringUtils.isBlank(detail)){
            return new JSONResult("收货地址信息不能为空", 510);
        }

        return new JSONResult("ok");
    }
}
