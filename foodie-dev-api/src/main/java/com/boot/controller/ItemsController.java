package com.boot.controller;
import com.boot.pojo.Items;
import com.boot.pojo.ItemsImg;
import com.boot.pojo.ItemsParam;
import com.boot.pojo.ItemsSpec;
import com.boot.pojo.vo.CommentLevelCountsVo;
import com.boot.pojo.vo.ItemInfoVo;
import com.boot.service.ItemService;
import com.boot.utils.JSONResult;
import com.boot.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags={"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController{

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情")
    @GetMapping("/info/{itemId}")
    public JSONResult getItemInfo(@ApiParam(name = "itemId", value = "商品id", required = true) @PathVariable String itemId){
        if(itemId == null || StringUtils.isBlank(itemId)){
            return new JSONResult("商品不存在", 506);
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        ItemsParam itemImgParam = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemImgSpecList = itemService.queryItemSpecList(itemId);

        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(item);
        itemInfoVo.setItemImgList(itemImgList);
        itemInfoVo.setItemImgParam(itemImgParam);
        itemInfoVo.setItemImgSpecList(itemImgSpecList);

        return new JSONResult(itemInfoVo);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(@ApiParam(name = "itemId", value = "商品id", required = true) @RequestParam String itemId){
        CommentLevelCountsVo countsVo = itemService.queryCommentCounts(itemId);
        return new JSONResult(countsVo);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true) @RequestParam String itemId,
            @ApiParam(name = "level", value = "评论等级", required = false) @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false) @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页数量", required = false) @RequestParam Integer pageSize
    ){
        if (StringUtils.isBlank(itemId)){
            return new JSONResult("商品不存在", 506);
        }
        if(page == null){
            page = 1;
        }
        if(pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);
        return new JSONResult(grid);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult commentLevel(
            @ApiParam(name = "keywords", value = "关键词", required = true) @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = false) @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false) @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页数量", required = false) @RequestParam Integer pageSize
    ){
        if (StringUtils.isBlank(keywords)){
            return new JSONResult("搜索语句不存在", 506);
        }
        if(page == null){
            page = 1;
        }
        if(pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);
        return new JSONResult(grid);
    }

    @ApiOperation(value = "通过分类id搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult commentLevel(
            @ApiParam(name = "catId", value = "三级分类id", required = true) @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false) @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false) @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页数量", required = false) @RequestParam Integer pageSize
    ){
        if (catId == null){
            return new JSONResult("商品不存在", 506);
        }
        if(page == null){
            page = 1;
        }
        if(pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return new JSONResult(grid);
    }
}
