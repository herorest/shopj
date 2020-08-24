package com.boot.service;

import com.boot.pojo.*;
import com.boot.pojo.vo.CommentLevelCountsVo;
import com.boot.pojo.vo.ItemCommentVo;
import com.boot.pojo.vo.ShopcartVo;
import com.boot.utils.PagedGridResult;

import java.util.List;

public interface ItemService {

    /***
     * 根据商品id查询详情
     * @param id
     * @return
     */
    public Items queryItemById(String id);

    /***
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /***
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /***
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据id查询商品评价
     * @param itemId
     */
    public CommentLevelCountsVo queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品评价
     * @param itemid
     * @param level
     * @return
     */
    public PagedGridResult queryPagedComments(String itemid, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);


    /**
     * 根据分类id搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中商品数据（用于刷新渲染购物车中的商品数据）
     * @param Ids
     * @return
     */
    public List<ShopcartVo> queryItemsBySpecIds(String Ids);

}
