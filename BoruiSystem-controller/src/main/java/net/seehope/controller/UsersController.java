package net.seehope.controller;

import io.swagger.annotations.ApiOperation;
import net.seehope.UserService;
import net.seehope.common.RestfulJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping("clients")
    public RestfulJson getAllClients(Integer page,Integer pageSize){
        return RestfulJson.isOk(userService.getClients(page,pageSize));
    }

    @DeleteMapping("clients")
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
