package com.github.wxpay.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.alipay.api.internal.util.AlipaySignature;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.service.AlipayService;
import com.github.wxpay.service.WxPayService;
import com.github.wxpay.vo.WxPayNotifyVO;
import lombok.extern.slf4j.Slf4j;
import net.seehope.OrdersService;
import net.seehope.SmsSendService;

import net.seehope.pojo.bo.PayBo;
import net.seehope.pojo.bo.WeChatPayBo;
import net.seehope.util.SmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
@Slf4j
@RestController
@RequestMapping("wxPay")
public class WxpayController {

    private static final String APPID = "wx22b4e8dc67f0ea0c";
    private static final String SECRET = "7c1355ff038ca93c0d49106ff367636e";

    /**支付宝应用设置本地公钥后生成对应的支付宝公钥（非本地生成的公钥）*/
    private static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArfd2HmjRDUjQLwYPp62kVi7wAoYViHCyurtNN5kd5vyEKvdATdz6JX5M5hUszacQVo7sydLWap1z0hyhg7qBU2qxfGS8Ge6/cX09RivT4WXRfC+0R7EFoNCUoKtwpAgrb5WWlG39v2wS9owTrntZCOgUS6FE8wLGiSJ/9hP3v9XuMbhEO+pRl6r4N+d9B9k2vmxuanNSnPv+3PjQQ8S5OM9S0bt5wMKH23QOnbKZQkgcljuQHephuI1p+OG2iAdH1Ku+Ub8221mP/5TIyPwgrzQGLefJCSgYkdWrRH6WjNSj7IGqP9CLM4G+vlEdlhJa6JmCCyUc6Vb09g9BvTwj+QIDAQAB";

    Logger logger = LoggerFactory.getLogger("alipay");

    @Autowired
    WxPayService wxPayService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    SmsSendService smsSendService;
    @Autowired
    AlipayService alipayService;



    @GetMapping(value = "/pay",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, String> pay(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) throws Exception {
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
        String userId = message[2];



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
        weChatPayBo.setUserId(userId);
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
           String userId = params[3];


            if(!ordersService.isOrderFinish(orderId)){
                ordersService.insertOrders(userId,orderId);
               String message =  SmsUtils.connect("code1",productNames,"code2",orderId);
                System.out.println("message"+message);
                smsSendService.sendSuccess(message,phone);
                ordersService.finishOrder(orderId);
            }

        }
        return WXPayUtil.mapToXml(result);
    }


    /**支付宝回调接口*/
    /**不返回success，支付宝会在25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）才会结束通知发送。*/
    @RequestMapping(value = "alinotify")
    public String aliNotify(HttpServletRequest request) throws Exception {
        try {
            log.info("进入支付宝回调地址");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            log.info("支付宝验签参数：{}", JSON.toJSONString(requestParams));
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }

                params.put(name, valueStr);



            }
//            System.out.println("------"+params.get("productNames") + params.get("orderId")+ params.get("userId") + params.get("phone"));


            String productNames = params.get("productNames");
           String orderId = params.get("orderId");
           String userId = params.get("userId");
           String phone = params.get("phone");

            boolean flag = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
            if (flag) {
                System.out.println("验签成功");
                alipayService.aliNotify(params);

                if(!ordersService.isOrderFinish(orderId)){
                    ordersService.insertOrders(userId,orderId);
                    String message =  SmsUtils.connect("code1",productNames,"code2",orderId);
                    System.out.println("message"+message);
                    smsSendService.sendSuccess(message,phone);
                    ordersService.finishOrder(orderId);
                }

                log.info("支付宝通知更改状态成功！");
                return "success";
            }
        } catch (Throwable e) {
            log.error("exception: ", e);
        }
        return "failure";
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
