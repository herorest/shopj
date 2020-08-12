package com.boot.service.impl;

import com.boot.mapper.CategoryMapper;
import com.boot.mapper.CategoryMapperCustom;
import com.boot.pojo.Category;
import com.boot.pojo.vo.CategoryVo;
import com.boot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceTmpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVo> getSubCatList(Integer rootCatId){
        List<CategoryVo> result = categoryMapperCustom.getSubCatList(rootCatId);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List getNewItemsLazy(Integer rootCatId){
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);

        return categoryMapperCustom.getNewItemsLazy(map);
    }
}
