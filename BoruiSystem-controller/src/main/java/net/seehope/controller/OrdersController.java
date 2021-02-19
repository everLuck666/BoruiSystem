package net.seehope.controller;

import io.swagger.annotations.ApiOperation;
import net.seehope.OrdersService;
import net.seehope.common.RestfulJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;


    //得到今天收入
    @ApiOperation(value = "得到今天收入",produces="application/json;charset=UTF-8")
    @GetMapping("income")
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
