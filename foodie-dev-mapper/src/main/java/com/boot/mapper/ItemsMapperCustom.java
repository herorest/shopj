package com.boot.mapper;

import com.boot.my.mapper.MyMapper;
import com.boot.pojo.Items;
import com.boot.pojo.vo.ItemCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {
    public List<ItemCommentVo> queryItemComments(@Param("paramsMap") Map<String, Object> map);
}