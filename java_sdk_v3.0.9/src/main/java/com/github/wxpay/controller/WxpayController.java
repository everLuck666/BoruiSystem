package com.github.wxpay.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.wxpay.bo.UserOrderInformationBo;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.service.AlipayService;
import com.github.wxpay.service.WxPayService;
import com.github.wxpay.vo.WxPayNotifyVO;
import net.seehope.OrdersService;
import net.seehope.SmsSendService;
import net.seehope.common.RestfulJson;
import net.seehope.jwt.JWTUtils;

import net.seehope.pojo.bo.PayBo;
import net.seehope.pojo.bo.WeChatPayBo;
import net.seehope.util.SmsUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("wxPay")
public class WxpayController {

    private static final String APPID = "wx22b4e8dc67f0ea0c";
    private static final String SECRET = "7c1355ff038ca93c0d49106ff367636e";

    @Autowired
    WxPayService wxPayService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    SmsSendService smsSendService;
    @Autowired
    AlipayService alipayService;



    @PostMapping(value = "/pay",produces="application/json;charset=UTF-8")
    @ResponseBody
    public RestfulJson pay(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) throws Exception {
        System.out.println("我进来了");
       // System.out.println("token是"+token);
        //itemService.isCanBug(userOrderInformationBo.getSpecies());//判断门票是否可以买
       // DecodedJWT verify = JWTUtils.getTokenInfo(token);
     //   String userId = verify.getClaim("openId").asString();
        List<Map<String, String>> productList = new ArrayList<>();
        PayBo payBo = new PayBo();

        String email = jsonObject.getString("email");
        payBo.setAddress(jsonObject.getString("address"));
        payBo.setEmail(jsonObject.getString("email"));
        payBo.setNote(jsonObject.getString("note"));
        payBo.setPhone(jsonObject.getString("phone"));

        payBo.setUserName(jsonObject.getString("userName"));

        payBo.setBank(jsonObject.getString("bank"));
        payBo.setInvoiceTitle(jsonObject.getString("invoiceTitle"));
        payBo.setAccount(jsonObject.getString("account"));
        payBo.setTaxId(jsonObject.getString("taxId"));
        payBo.setFlag(jsonObject.getString("flag"));//判断是否加入用户体验计划
        payBo.setInvoiceFlag(jsonObject.getString("invoiceFlag"));//是否需要发票
        payBo.setInvoiceType(jsonObject.getString("invoiceType"));
        payBo.setNote(jsonObject.getString("remark"));//备注

        String payType = jsonObject.getString("payType");






        JSONArray jsonArray = jsonObject.getJSONArray("product");
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer body = new StringBuffer();

        Map<String,String> map = new HashMap<>();
        for (int i = 0;i<jsonArray.size();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            String pno  = object.getString("item");
            String number = object.getString("num");
            map.put(pno,number);
            body.append(pno);
            body.append("*");
            body.append(number);
            body.append("~");
            stringBuffer.append(pno);
            stringBuffer.append("~");
        }
        payBo.setProductList(map);

        String[] message = ordersService.pretreatment(payBo);
        String orderId = message[0];
        int totalPrice = Integer.parseInt(message[1]);




        String userId = "123";
        System.out.println("---------userId:"+userId);

        // 获取请求ip地址
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf(",") != -1) {
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
       // return wxPayService.wxPay(userId,ip);
        WeChatPayBo weChatPayBo = new WeChatPayBo();
        weChatPayBo.setBody(body.toString().substring(0,body.length()-1));
        weChatPayBo.setIpAddress(ip);
        weChatPayBo.setOrderId(orderId);
        weChatPayBo.setPhone(payBo.getPhone());
        weChatPayBo.setTotalPrice(totalPrice);
        System.out.println(stringBuffer.toString().substring(0,stringBuffer.length()-1));
        weChatPayBo.setProductNames(stringBuffer.toString().substring(0,stringBuffer.length()-1));
        if(payType.equals("微信")){
            wxPayService.doWx(request,response,weChatPayBo);
        }else{
           alipayService.alipay(response,weChatPayBo);
        }


        return null;
    }

    @RequestMapping(value = "/success", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String success(HttpServletRequest request, @RequestBody WxPayNotifyVO param) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        System.out.println("开始下单了");
        if ("SUCCESS".equals(param.getReturn_code())) {


            result.put("return_code", "SUCCESS");
            result.put("return_msg", "OK");


            System.out.println("attach:"+param.getAttach());
           String[] params = param.getAttach().split("#");
           String orderId = params[0];
           String phone = params[1];
           String productNames = params[2];
            if(!ordersService.isOrderFinish(orderId)){
               String message =  SmsUtils.connect("code1",productNames,"code2",orderId);
                System.out.println("message"+message);
                smsSendService.sendSuccess(message,phone);
                ordersService.finishOrder(orderId);
            }

        }
        return WXPayUtil.mapToXml(result);
    }

    @RequestMapping("xiao")
    public void sss(){
        System.out.println("lal");
    }

//
//    @RequestMapping(value="/notifyWeiXinPay",produces="text/html;charset=utf-8")
//    @ResponseBody
//    public String notifyWeiXinPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Map<String,String> return_data = new HashMap<String,String>();
//        //读取参数
//        InputStream inputStream ;
//        StringBuffer sb = new StringBuffer();
//        inputStream = request.getInputStream();
//        String s ;
//        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//        while ((s = in.readLine()) != null){
//            sb.append(s);
//        }
//        in.close();
//        inputStream.close();
//
//        //解析xml成map
//        Map<String, String> map = WXPayUtil.xmlToMap(sb.toString());
//        //判断签名是否正确
////        if(true) {
//
//        if(WXPayUtil.isSignatureValid(map, ConfigUtil.API_KEY)) {
//            if(!map.get("return_code").toString().equals("SUCCESS")){
//                return_data.put("return_code", "FAIL");
//                return_data.put("return_msg", "return_code不正确");
//            }else{
//                if(!map.get("result_code").toString().equals("SUCCESS")){
//                    return_data.put("return_code", "FAIL");
//                    return_data.put("return_msg", "result_code不正确");
//                    return WXPayUtil.mapToXml(return_data);
//                }
//
//                String orderno = (String)map.get("out_trade_no");//商户订单号
//                String transaction_id = (String)map.get("transaction_id");//微信支付订单号
//                String time_end = (String)map.get("time_end");//支付完成时间yyyyMMddHHmmss
//                BigDecimal total_fee = new BigDecimal(map.get("total_fee").toString());
//                //付款完成后，支付宝系统发送该交易状态通知
//                Productorder order = productorderService.findUniqueByProperty("order_no", orderno);
//                if(order==null) {
//                    System.out.println("订单不存在");
//                    return_data.put("return_code", "FAIL");
//                    return_data.put("return_msg", "订单不存在");
//                    return WXPayUtil.mapToXml(return_data);
//                }
//
//                BigDecimal num = new BigDecimal("100");
//                BigDecimal ordermoney = new BigDecimal(order.getOrdermoney());
//                ordermoney  = ordermoney.multiply(num);
//                //订单已经支付
//                if(order.getOrderstatus().equals("1")){
//                    System.out.println("订单已经支付");
//                    return_data.put("return_code", "SUCCESS");
//                    return_data.put("return_msg", "OK");
//                    return WXPayUtil.mapToXml(return_data);
//                }
//                //如果支付金额不等于订单金额返回错误
//                if(ordermoney.compareTo(total_fee)!=0){
//                    System.out.println("资金异常");
//                    return_data.put("return_code", "FAIL");
//                    return_data.put("return_msg", "金额异常");
//                    return WXPayUtil.mapToXml(return_data);
//                }
//                //更新订单信息
//                try {
//                    System.out.println("更新订单信息");
//                    SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
//                    SimpleDateFormat sdf2=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
//                    order.setOrderstatus("1");
//                    order.setPaytime(sdf2.format(sdf1.parse(time_end)));
//                    order.setPaymentway("1");
//                    productorderService.update(order);
//
//                    System.out.println("插入已经支付的订单表");
//                    //插入已经支付的订单表（product_order_pay）
//                    List<String> goodsIds = productgoodsService.getGoodsIdsByOrderno(orderno);
//                    for (String gid : goodsIds) {
//                        Productorderpay productorderpay = new Productorderpay();
//                        productorderpay.setUserid(order.getUserid());
//                        productorderpay.setPpid(gid);
//                        productorderpay.setEndtime(order.getOrderendtime());
//                        productorderpayService.insert(productorderpay);
//                    }
//
//                    return_data.put("return_code", "SUCCESS");
//                    return_data.put("return_msg", "OK");
//                    return WXPayUtil.mapToXml(return_data);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return_data.put("return_code", "FAIL");
//                    return_data.put("return_msg", "更新订单失败");
//                    return WXPayUtil.mapToXml(return_data);
//                }
//            }
//        } else{
//            System.out.println("通知签名验证失败");
//            return_data.put("return_code", "FAIL");
//            return_data.put("return_msg", "签名错误");
//        }
//        return WXPayUtil.mapToXml(return_data);
//    }
//






}
