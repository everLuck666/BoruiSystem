package net.seehope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.seehope.SmsSendService;
import net.seehope.common.RestfulJson;
import net.seehope.common.UserType;
import net.seehope.pojo.Send;
import net.seehope.util.HttpUtils;
import net.seehope.util.MD5Utils;
import net.seehope.util.SmsUtils;
import org.apache.http.HttpResponse;
import org.eclipse.jetty.util.security.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("sms")
@Api("发送短息")
@CrossOrigin(origins = "*",maxAge = 3600)
public class SmsController {

    @Autowired
    SmsSendService smsSendService;

    @PostMapping(value = "/sms" ,produces="application/json;charset=UTF-8")
    @ApiOperation("发送新产品短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "要发送的短信信息", dataType = "String"),
            @ApiImplicitParam(name = "identity", value = "要发送给的对象，如果是要发送给全部客户就可以不填，如果是发送给订阅客户就随便穿个值过来就行", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "登录得到的token", dataType = "String")
    })
    public RestfulJson sendnewProduct(@RequestBody Map map, HttpServletRequest request) throws NoSuchAlgorithmException {
        String message = map.get("message").toString();
        String identity = null;
        if(map.get("identity") != null){
            identity = map.get("identity").toString();
        }


        String messageValue = SmsUtils.connect("code1",message);

        String managerName = request.getAttribute("managerName").toString();

        if(identity == null){
            System.out.println("我执行了");
            smsSendService.sendAllPeople(messageValue,managerName);
        }else{
            System.out.println("我执行过了");
            smsSendService.sendPeopel(messageValue,managerName);
        }
        return RestfulJson.isOk("发送成功");

    }

    @RequestMapping(value = "/sms2",produces="application/json;charset=UTF-8")
    @ApiOperation("发送快递接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deliveryId", value = "快递单号", dataType = "String"),
            @ApiImplicitParam(name = "productName", value = "商品的名字", dataType = "String"),
           @ApiImplicitParam(name = "phone", value = "要发送的手机号", dataType = "String"),
            @ApiImplicitParam(name = "deliveryName", value = "快递的名称", dataType = "String")
    })
    public RestfulJson willsendProduct(@RequestBody Map map) throws NoSuchAlgorithmException {
        String deliveryId = map.get("deliveryId").toString();
        String phone = map.get("phone").toString();
        String productName = map.get("productName").toString();
        String deliveryName = map.get("deliveryName").toString();

        String message = SmsUtils.connect("code1",productName,"code2",deliveryName,"code3",deliveryId);


        Send send  = new Send();
        send.setInformation(message);

        smsSendService.sendFlow(phone,send);
        return RestfulJson.isOk("发送成功");
    }
//    @RequestMapping("/sms3" )
//    public RestfulJson sendSuccessfulProduct() throws NoSuchAlgorithmException {
//        return RestfulJson.isOk("发送成功");
//    }

    @GetMapping(value = "/information",produces="application/json;charset=UTF-8")
    @ApiOperation("得到通知记录")
    public RestfulJson getInformation(){
        return RestfulJson.isOk(smsSendService.getAllInformatin());
    }
}
