package net.seehope.controller;

import io.swagger.annotations.ApiOperation;
import net.seehope.UserService;
import net.seehope.common.RestfulJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping(value = "clients",produces="application/json;charset=UTF-8")
    public RestfulJson getAllClients(Integer page,Integer pageSize){
        return RestfulJson.isOk(userService.getClients(page,pageSize));
    }

    @DeleteMapping(value = "clients",produces="application/json;charset=UTF-8")
    public RestfulJson deleteClients(String userId){
        userService.deleteClient(userId);
        return RestfulJson.isOk("删除客户成功");
    }

    @ApiOperation("导出订单数据接口")
    @GetMapping("/exportClients")
    public ResponseEntity<byte[]> exportOrdersExcel(HttpServletRequest request, HttpServletResponse response) {
        String excelName = "客户信息表";
        return userService.exportClientExcel(request,response,excelName);
    }
}
