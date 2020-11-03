package com.boot.mapper;

import com.boot.my.mapper.MyMapper;
import com.boot.pojo.Items;
import com.boot.pojo.vo.MyCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<Items> {
    public void saveComments(Map<String, Object> map);

    public List<MyCommentVo> queryMyComments(@Param("paramsMap") Map<String, Object> map);
}