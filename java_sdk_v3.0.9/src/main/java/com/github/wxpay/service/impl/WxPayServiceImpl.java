package com.github.wxpay.service.impl;

import com.github.wxpay.bo.UserOrderInformationBo;
import com.github.wxpay.constant.WechatConstant;
import com.github.wxpay.sdk.*;
import com.github.wxpay.service.WxPayService;
import com.github.wxpay.util.CommonUtil;
import com.github.wxpay.util.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;
import net.seehope.pojo.bo.WeChatPayBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {

    @Override
    @Transactional
    public Map<String, String> wxPay(String openId, String ipAddress) throws Exception {


        // Integer totalSum = Integer.valueOf(items.getPrice())*Integer.valueOf(bo.getTicketNum());
        int totalSum = 1;

        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("body", "21"); // 商家名称-销售商品类⽬、String(128)
        paraMap.put("openid", "o-7Fs5BkMLVF9OpnA8A9eN_duo0k"); // openId，通过登录获取
        paraMap.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-", ""));// 订单号,每次都不同 paraMap.put("spbill_create_ip", ipAddress);
        paraMap.put("attach", "");
        // paraMap.put("total_fee", "1"); // ⽀付⾦额，单位分，即0.01元
        paraMap.put("total_fee", totalSum + ""); // ⽀付⾦额，单位分，即0.01元
        paraMap.put("trade_type", "JSAPI");
        paraMap.put("spbill_create_ip", ipAddress);

        // 2. 通过MyWXPayConfig创建WXPay对象，⽤于⽀付请求
        final String SUCCESS_NOTIFY = "https://sportsmonster.ltd/wxPay/success";
        boolean useSandbox = false; // 是否使⽤沙盒环境⽀付API，是的话不会真正扣钱 WXPayConfig config = new MyWXPayConfig();
        WXPayConfig config = new MyWXPayConfig();
        WXPay wxPay = new WXPay(config, SUCCESS_NOTIFY, false, useSandbox);

        Map<String, String> map = wxPay.unifiedOrder(wxPay.fillRequestData(paraMap), 15000, 15000);//这个方法里面包含一次签名


        // 4. 发送post请求"统⼀下单接⼝"(https://api.mch.weixin.qq.com/pay/unifiedorder), 返回预⽀付id:prepay_id String prepayId = (String) map.get("prepay_id");
        String prepayId = (String) map.get("prepay_id");
        System.out.println("我拿到的预处理id是" + prepayId);


        String code_url = map.get("result_code");
        System.out.println("代码是" + code_url);
        Map<String, String> payMap = new HashMap<String, String>();
        payMap.put("appId", WechatConstant.APPID);
        payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
        payMap.put("nonceStr", WXPayUtil.generateNonceStr());
        if (useSandbox) {
            payMap.put("signType", WXPayConstants.MD5);

        } else {
            payMap.put("signType", WXPayConstants.HMACSHA256);
        }
        payMap.put("package", "prepay_id=" + prepayId);
        //通过appid,timeStamp,nonceStr,signType,package及商户密钥进行key=value拼接
        String paySign = null;
        if (useSandbox) {

            paySign = WXPayUtil.generateSignature(payMap, WechatConstant.MCH_KEY, WXPayConstants.SignType.MD5);
        } else {

            paySign = WXPayUtil.generateSignature(payMap, WechatConstant.MCH_KEY, WXPayConstants.SignType.HMACSHA256);
        }
        payMap.put("paySign", paySign);
        return payMap;
    }


    @Override
    public void doWx(HttpServletRequest request, HttpServletResponse response, WeChatPayBo weChatPayBo) throws Exception {
        String number = request.getParameter("number") == null ? "" : request.getParameter("number");

        System.out.println("lalall:"+weChatPayBo.getOrderId());
        // Productorder p = productorderService.findUniqueByProperty("number", number);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStart = sdf.format(date);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date date1 = cal.getTime();
        String timeExpire = sdf.format(date1);
        SortedMap<String, String> parameters = new TreeMap<String, String>();
        parameters.put("appid", WechatConstant.APPID);
        parameters.put("body", weChatPayBo.getBody());
        parameters.put("attach",weChatPayBo.getOrderId()+"#"+weChatPayBo.getPhone()+"#"+weChatPayBo.getProductNames()+"#"+weChatPayBo.getUserId());
        parameters.put("mch_id", WechatConstant.MCH_ID);
        parameters.put("out_trade_no", weChatPayBo.getOrderId());
        parameters.put("spbill_create_ip", weChatPayBo.getIpAddress());
        DecimalFormat df = new DecimalFormat("#");
        parameters.put("total_fee",String.valueOf(weChatPayBo.getTotalPrice()));
        parameters.put("trade_type", "NATIVE");
     //   parameters.put("out_trade_no","123");
       // parameters.put("time_expire", CommonUtil.getOrderExpireTime(startData,5*60*1000L));//二维码过期时间5分钟
        parameters.put("nonce_str", WXPayUtil.generateNonceStr());
        parameters.put("notify_url", WechatConstant.SUCCESS_NOTIFY);//支付成功后回调的action，与JSAPI相同
        String generateSignature = WXPayUtil.generateSignature(parameters, WechatConstant.MCH_KEY, WXPayConstants.SignType.MD5);
        parameters.put("sign", generateSignature);
        String generateSignedXml = WXPayUtil.generateSignedXml(parameters, WechatConstant.MCH_KEY);
        System.out.println("微信支付预下单请求xml格式：：" + generateSignedXml);
        String result = CommonUtil.httpsRequest( "https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", generateSignedXml);
        System.out.println(result);
        Map<String, String> map;
        try {

            map = WXPayUtil.xmlToMap(result);
            String returnCode = map.get("return_code");
            String resultCode = map.get("result_code");
            if (returnCode.equalsIgnoreCase("SUCCESS") && resultCode.equalsIgnoreCase("SUCCESS")) {
                String codeUrl = map.get("code_url");
                //TODO 拿到codeUrl，写代码生成二维码
                System.out.println("codeUrl=" + codeUrl);
                int width = 300;
                int height = 300;
                //二维码的图片格式
                String format = "JPEG";
                Hashtable hints = new Hashtable();
                //内容所使用编码
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl,
                        BarcodeFormat.QR_CODE, width, height, hints);
                // response.setContentType("image/JPEG");
                MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
