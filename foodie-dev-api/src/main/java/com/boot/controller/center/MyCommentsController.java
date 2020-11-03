package com.boot.controller.center;

import com.boot.controller.BaseController;
import com.boot.pojo.OrderItems;
import com.boot.pojo.Orders;
import com.boot.service.MyCommentsService;
import com.boot.service.center.MyOrdersService;
import com.boot.utils.JSONResult;
import com.boot.utils.PagedGridResult;
import enums.YesOrNo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "center-用户中心我的评价", tags = {"用户中心我的评价的相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    MyCommentsService myCommentsService;

    @Autowired
    MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending")
    public JSONResult commentLevel(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId
    ){
        JSONResult result = checkUserOrder(orderId, userId);
        if(result.getState() != JSONResult.SUCCESS){
            return result;
        }

        //判断是否已经评价过
        Orders myOrder = (Orders)result.getData();
        if(myOrder.getIsComment() == YesOrNo.YES.type){
            return new JSONResult("该笔订单已经评价", 518);
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return new JSONResult(list);
    }

    /**
     * 用于验证用户和订单是否有关联
     * @return
     */
    private JSONResult checkUserOrder(String orderId, String userId){
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if(order == null){
            return new JSONResult("订单不存在", 515);
        }
        return new JSONResult(order);
    }
}
