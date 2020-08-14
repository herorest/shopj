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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class BaseController {
    public static final Integer COMMENT_PAGE_SIZE = 10;
}
