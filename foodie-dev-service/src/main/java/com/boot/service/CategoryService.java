package com.boot.service;

import com.boot.pojo.Category;
import com.boot.pojo.vo.CategoryVo;
import com.boot.pojo.vo.NewItemsVo;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有轮播图列表
     * @return List
     */
    public List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类信息
     * @param rootCatId
     * @return
     */

    public List<CategoryVo> getSubCatList(Integer rootCatId);


    /***
     * 查询每个一级分类下的子分类
     * @param rootCatId
     * @return
     */
    public List<NewItemsVo> getNewItemsLazy(Integer rootCatId);

}
