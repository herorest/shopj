package com.boot.service;

import com.boot.pojo.*;

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

}
