package com.boot.service;

import com.boot.pojo.Carousel;
import com.boot.pojo.Users;
import com.boot.pojo.bo.UserBo;

import java.util.List;

public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return List
     */
    public List<Carousel> queryAll(Integer isShow);
}
