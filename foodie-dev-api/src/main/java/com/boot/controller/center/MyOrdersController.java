package com.boot.controller.center;

import com.boot.controller.BaseController;
import com.boot.pojo.Orders;
import com.boot.pojo.Users;
import com.boot.service.center.CenterUserService;
import com.boot.service.center.MyOrdersService;
import com.boot.utils.JSONResult;
import com.boot.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "center-用户中心我的订单", tags = {"用户中心展示我的订单的相关接口"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public JSONResult commentLevel(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false) @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false) @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页数量", required = false) @RequestParam Integer pageSize
    ){
        if (StringUtils.isBlank(userId)){
            return new JSONResult("用户不存在", 514);
        }
        if(page == null){
            page = 1;
        }
        if(pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return new JSONResult(grid);
    }

    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public JSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId
    ){
        if(StringUtils.isBlank(orderId)){
            return new JSONResult("订单不存在", 515);
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return new JSONResult("ok");
    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId
    ){
        if(StringUtils.isBlank(orderId) ){
            return new JSONResult("订单不存在", 515);
        }

        JSONResult result = checkUserOrder(orderId, userId);
        if(result.getState() != JSONResult.SUCCESS){
            return result;
        }

        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if(!res){
            return new JSONResult("订单确认收货失败", 517);
        }

        return new JSONResult("ok");
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
        return new JSONResult("ok");
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId
    ){
        if(StringUtils.isBlank(orderId) ){
            return new JSONResult("订单不存在", 515);
        }

        JSONResult result = checkUserOrder(orderId, userId);
        if(result.getState() != JSONResult.SUCCESS){
            return result;
        }

        boolean res = myOrdersService.deleteOrder(userId, orderId);
        if(!res){
            return new JSONResult("删除订单失败", 518);
        }

        return new JSONResult("ok");
    }
}
