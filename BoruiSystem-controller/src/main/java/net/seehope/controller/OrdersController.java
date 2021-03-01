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

import io.swagger.annotations.ApiOperation;
import net.seehope.OrdersService;
import net.seehope.common.RestfulJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



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

    @ApiOperation("获取销量统计上面部分数据")
    @GetMapping(value = "sales",produces="application/json;charset=UTF-8")
    public RestfulJson getSalesStatistic(){
        return RestfulJson.isOk(ordersService.salesStatistic());
    }

    @ApiOperation("获取销量统计合计部分数据")
    @GetMapping(value = "total",produces="application/json;charset=UTF-8")
    public RestfulJson getTotalStatistic(){
        return RestfulJson.isOk(ordersService.totalStatistic());
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

    @ApiOperation("获取发票信息")
    @GetMapping("invoices")
    public RestfulJson getInvoices(String orderId){
        return RestfulJson.isOk(ordersService.getInvoices(orderId));
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




    //得到今天收入
    @ApiOperation(value = "得到今天收入",produces="application/json;charset=UTF-8")
    @GetMapping(value = "income",produces="application/json;charset=UTF-8")
    public RestfulJson getTodayIncome(){

        return RestfulJson.isOk(ordersService.getTodayIncome());

    }

    //得到本月收入
    @ApiOperation("得到本月收入")
    @GetMapping(value = "incomeMonth",produces="application/json;charset=UTF-8")
    public RestfulJson getMonthIncome(){

        return RestfulJson.isOk(ordersService.getMonthIncome());

    }
    //累计收入
    @ApiOperation("得到累计收入")
    @GetMapping(value = "totalIncome",produces="application/json;charset=UTF-8")
    public RestfulJson getTotalIncome(){
        return RestfulJson.isOk(ordersService.totalIncome());
    }
    //得到多天的每天数据
    @ApiOperation("得到多天的每天数据")
    @GetMapping(value = "all",produces="application/json;charset=UTF-8")
    public RestfulJson getAllOrderIncome(){
        return RestfulJson.isOk(ordersService.getAllIncome());
    }
}
