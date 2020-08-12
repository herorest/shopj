package com.boot.controller;
import com.boot.pojo.Carousel;
import com.boot.pojo.Category;
import com.boot.pojo.vo.CategoryVo;
import com.boot.pojo.vo.NewItemsVo;
import com.boot.service.CarouselService;
import com.boot.service.CategoryService;
import com.boot.utils.JSONResult;
import enums.YesOrNo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页", tags={"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表")
    @GetMapping("/carousel")
    public JSONResult carousel(){
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return new JSONResult(list);
    }

    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）")
    @GetMapping("/cats")
    public JSONResult cats(){
        List<Category> list = categoryService.queryAllRootLevelCat();
        return new JSONResult(list);
    }

    @ApiOperation(value = "获取商品分类（子分类）", notes = "获取商品分类（子分类）")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(@ApiParam(name = "rootCatId", value = "一级分类id", required = true) @PathVariable Integer rootCatId){
        if(rootCatId == null){
            return new JSONResult("分类不存在", 505);
        }
        List<CategoryVo> list = categoryService.getSubCatList(rootCatId);

        return new JSONResult(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据")
    @GetMapping("/newItems/{rootCatId}")
    public JSONResult getNewItemsLazy(@ApiParam(name = "rootCatId", value = "一级分类id", required = true) @PathVariable Integer rootCatId){
        if(rootCatId == null){
            return new JSONResult("分类不存在", 505);
        }
        List<NewItemsVo> list = categoryService.getNewItemsLazy(rootCatId);

        return new JSONResult(list);
    }
}
