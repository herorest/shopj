package com.boot.mapper;

import com.boot.pojo.vo.MyOrdersVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapperCustom {
    public List<MyOrdersVo> queryMyOrders(@Param("paramsMap") Map<String, Object> map);
}