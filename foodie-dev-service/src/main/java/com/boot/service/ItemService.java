package com.boot.service;

import com.boot.pojo.*;
import com.boot.pojo.vo.CommentLevelCountsVo;
import com.boot.pojo.vo.ItemCommentVo;
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

}
