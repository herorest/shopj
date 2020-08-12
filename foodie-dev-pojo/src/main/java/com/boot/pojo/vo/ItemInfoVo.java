package com.boot.pojo.vo;

import com.boot.pojo.Items;
import com.boot.pojo.ItemsImg;
import com.boot.pojo.ItemsParam;
import com.boot.pojo.ItemsSpec;

import java.util.List;

/**
 * 商品详情vo
 */
public class ItemInfoVo {
    private Items item;
    private List<ItemsImg> itemImgList;
    private ItemsParam itemImgParam;
    private List<ItemsSpec> itemImgSpecList;

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public ItemsParam getItemImgParam() {
        return itemImgParam;
    }

    public void setItemImgParam(ItemsParam itemImgParam) {
        this.itemImgParam = itemImgParam;
    }

    public List<ItemsSpec> getItemImgSpecList() {
        return itemImgSpecList;
    }

    public void setItemImgSpecList(List<ItemsSpec> itemImgSpecList) {
        this.itemImgSpecList = itemImgSpecList;
    }
}
