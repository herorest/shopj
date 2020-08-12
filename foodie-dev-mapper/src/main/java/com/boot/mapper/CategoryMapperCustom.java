package com.boot.mapper;

import com.boot.pojo.vo.CategoryVo;
import com.boot.pojo.vo.NewItemsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {
    public List<CategoryVo> getSubCatList(Integer rootCatId);

    public List<NewItemsVo> getNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}