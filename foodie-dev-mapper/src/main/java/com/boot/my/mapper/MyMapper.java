package com.boot.my.mapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用mapper
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
