package net.seehope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.seehope.UserService;
import net.seehope.common.RestfulJson;
import net.seehope.common.UserType;
import net.seehope.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "管理员管理",value = "UserManagerController")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserManagerController {
    @Autowired
    UserService userService;

    @PutMapping(value = "user",produces="application/json;charset=UTF-8")
    @ApiOperation("添加管理员")
    @ApiImplicitParams({@ApiImplicitParam(name ="userName",value = "姓名",dataType = "String"),
            @ApiImplicitParam(name ="userId",value = "账号",dataType = "String"),
            @ApiImplicitParam(name ="password",value = "密码",dataType = "String")
    })
    public RestfulJson addManager(@RequestBody Users users){
        userService.insertUser(users, UserType.SUPERMANAGER.getType());
        return RestfulJson.isOk("添加管理员成功");
    }

    @DeleteMapping(value = "user",produces="application/json;charset=UTF-8")
    @ApiOperation("删除管理员")
    @ApiImplicitParams({@ApiImplicitParam(name ="userId",value = "账号",dataType = "String"),
    })
    public RestfulJson deleteManager(@RequestBody Map map){
        userService.deleteUser(map.get("userId").toString());
        return RestfulJson.isOk("删除管理员成功");
    }
    //得到所有管理员
    @GetMapping(value = "user",produces="application/json;charset=UTF-8")
    @ApiOperation("得到所有的管理员信息")
    public RestfulJson getAllManagers(){
        return RestfulJson.isOk(userService.getAllManagers());
    }



}