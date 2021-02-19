package net.seehope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.seehope.OrdersService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.bo.GetOrdersBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(tags="后台订单管理接口",value = "OrdersController")
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @ApiOperation("获取待处理订单数量")
    @GetMapping(value = "waiting",produces="application/json;charset=UTF-8")
    public RestfulJson getWaitingOrders(){
        return RestfulJson.isOk(ordersService.getWaitingOrders());
    }

    @ApiOperation("获取已完成订单数量")
    @GetMapping(value = "finished",produces="application/json;charset=UTF-8")
    public RestfulJson getFinishedOrders(){
        return RestfulJson.isOk(ordersService.getFinishedOrders());
    }

    @ApiOperation("根据条件获取订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页的数量大小", dataType = "String"),
            @ApiImplicitParam(name = "status",value = "订单状态", dataType = "Double"),
            @ApiImplicitParam(name = "orderId", value = "订单号", dataType = "String")
    })
    @GetMapping(value = "orders",produces="application/json;charset=UTF-8")
    public RestfulJson getAllOrders(GetOrdersBo ordersBo){
        return RestfulJson.isOk(ordersService.getAllOrders(ordersBo));
    }

    @ApiOperation("修改订单状态")
    @ApiImplicitParam(name = "orderId",value = "订单号",dataType = "String")
    @PostMapping(value = "updateOrder",produces="application/json;charset=UTF-8")
    public RestfulJson updateOrder(String orderId){
        ordersService.updateOrder(orderId);
        return RestfulJson.isOk("发货成功");
    }

    // 导出excel
    @ApiOperation("导出订单数据接口")
    @GetMapping("/exportOrders")
    public ResponseEntity<byte[]> exportOrdersExcel(HttpServletRequest request, HttpServletResponse response) {
        String excelName = "订单记录表";
        return ordersService.exportOrderExcel(request,response,excelName);
    }


}

