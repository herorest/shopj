package com.boot.service.impl;

import com.boot.mapper.*;
import com.boot.pojo.*;
import com.boot.pojo.vo.CommentLevelCountsVo;
import com.boot.pojo.vo.ItemCommentVo;
import com.boot.pojo.vo.SearchItemsVo;
import com.boot.pojo.vo.ShopcartVo;
import com.boot.service.ItemService;
import com.boot.utils.DesensitizationUtil;
import com.boot.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.CommentLevel;
import enums.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ItemServiceTmpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Override
    public Items queryItemById(String id) {
        return itemsMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example itemsImgExp = new Example(ItemsImg.class);
        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(itemsImgExp);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemSpecExp = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemSpecExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(itemSpecExp);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example itemsParamExp = new Example(ItemsParam.class);
        Example.Criteria criteria = itemsParamExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(itemsParamExp);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVo queryCommentCounts(String itemId){
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer totalCounts = goodCounts + badCounts + normalCounts;

        CommentLevelCountsVo countsVo = new CommentLevelCountsVo();
        countsVo.setBadCounts(badCounts);
        countsVo.setGoodCounts(goodCounts);
        countsVo.setNormalCounts(normalCounts);
        countsVo.setTotalCounts(totalCounts);
        return countsVo;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if(level != null){
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);

        //mybatis-pagehelper
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVo> list = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVo vo : list){
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        PagedGridResult grid = setterPagedGrid(list, page);
        return grid;
    }

    private PagedGridResult setterPagedGrid(List<?> list, Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        //mybatis-pagehelper
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVo> list = itemsMapperCustom.searchItems(map);
        PagedGridResult grid = setterPagedGrid(list, page);
        return grid;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVo> list = itemsMapperCustom.searchItemsByThirdCat(map);
        PagedGridResult grid = setterPagedGrid(list, page);
        return grid;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ShopcartVo> queryItemsBySpecIds(String specIds) {
        String ids[] = specIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList, ids);
        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result == null ? "" : result.getUrl();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        int result = itemsMapperCustom.decreaseItemSpecStock(specId, buyCounts);
        if(result != 1){
            throw new RuntimeException("订单创建失败，库存不足");
        }
    }
}
