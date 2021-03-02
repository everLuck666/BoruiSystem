package net.seehope.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.seehope.SmsSendService;
import net.seehope.UserService;
import net.seehope.common.SmsConstant;
import net.seehope.common.UserType;
import net.seehope.mapper.SendMapper;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Send;
import net.seehope.pojo.Users;
import net.seehope.pojo.bo.SmsBo;
import net.seehope.pojo.bo.StoreSendBo;
import net.seehope.util.HttpUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
public class SmsSendServiceImpl implements SmsSendService {
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    SendMapper sendMapper;
//    @Override
//    public void sendMessage(String message, String template) {
//        String host = "http://yzx.market.alicloudapi.com";
//        String path = "/yzx/sendSms";
//        String method = "POST";
//        String appcode = "13085d2cc8cc4050bc9e377b5d091096";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("mobile", "18555477301");
//        querys.put("param", "code:1234");
//        //querys.put("param", "这里填写你和商家定义的变量名称和变量值填写格式看上一行代码");
//        querys.put("tpl_id", "TP1804088");
//        Map<String, String> bodys = new HashMap<String, String>();
//
//
//        try {
//            /**
//             * 重要提示如下:
//             * HttpUtils请从
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//             * 下载
//             *
//             * 相应的依赖请参照
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//             */
//            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            System.out.println(response.toString());
//
//            //获取response的body
//            //System.out.println(EntityUtils.toString(response.getEntity()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 配置您申请的tradeKey
     */
    private static final String TRADE_KEY = "6b055e3ed008439dae245b007981d863";
    /**
     * 返回码字符
     */
    private static final String RETURN_CODE = "return_code";
    /**
     * 返回成功时返回码的值
     */
    private static final String SUCCESS = "0000";
    /**
     * 发送短信地址
     */
    private static final String REQUEST_URL = "http://api.yunzhixin.com:11140/txp/sms/send";

    @Override
   public void sendMessage(SmsBo bo){
//组装请求参数
        Map<String, String> smsRequestParam = new HashMap<>(16);
//用户编号，注册www.yunzhixin.com的手机号码
        smsRequestParam.put("account", SmsConstant.account);
//需要发送的手机号
        smsRequestParam.put("mobile", bo.getMobile());
//商户提交的订单号（商户保证其唯一性）
        smsRequestParam.put("order_id",bo.getOrderId());
//用户服务器时间戳(13位),JDK8版本可以使用String.valueOf(Instant.now().toEpochMilli())获取,JDK7版本可以使用String.valueOf(System.currentTimeMillis())获取
        smsRequestParam.put("time", String.valueOf(Instant.now().toEpochMilli()));
//模板编号
        smsRequestParam.put("tpl_id", bo.getTemplate());
//短信所需传入的参数
        smsRequestParam.put("params", bo.getMessage());
        requestCharge(smsRequestParam);
    }

    @Override
    @Transactional
    public void sendAllPeople(String message,String managerName) {


        List<StoreSendBo> usersList = usersMapper.getAllPeoplePhone("");
        SmsBo smsBo = new SmsBo();

        for (StoreSendBo storeSendBo:usersList){
            System.out.println(storeSendBo.getPhone());
            System.out.println(storeSendBo.getUserName());

            smsBo.setMessage(message);
            smsBo.setMobile(storeSendBo.getPhone());
            smsBo.setOrderId(UUID.randomUUID().toString());
            smsBo.setTemplate(SmsConstant.newProductTemplate);
            sendMessage(smsBo);
        }
        Send send  = new Send();
        String messageStorage = "尊敬的客户您好！感谢您对本公司的信任，我司新品"+message.split(":")[1]+" 即将上市，诚邀您体验新品！退订回T";
        send.setInformation(messageStorage);
        send.setManagerName(managerName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        send.setTime(simpleDateFormat.format(date));
        send.setUserType(UserType.ALL.getSubscribe_status());
        sendMapper.insert(send);

    }

    @Override
    public void sendPeopel(String message,String managerName) {

        List<StoreSendBo> usersList = usersMapper.getAllPeoplePhone("订阅");

        SmsBo smsBo = new SmsBo();

        for(StoreSendBo storeSendBo:usersList){
            smsBo.setMessage(message);
            smsBo.setMobile(storeSendBo.getPhone());
            smsBo.setOrderId(UUID.randomUUID().toString());
            smsBo.setTemplate(SmsConstant.newProductTemplate);
            sendMessage(smsBo);
        }



        Send send  = new Send();
        String messageStorage = "尊敬的客户您好！感谢您对本公司的信任，我司新品"+message.split(":")[1]+" 即将上市，诚邀您体验新品！退订回T";
        send.setInformation(messageStorage);
        send.setManagerName(managerName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        send.setTime(simpleDateFormat.format(date));
        send.setUserType(UserType.SUBSCRIBESTATUS.getSubscribe_status());
        sendMapper.insert(send);
    }

    @Override
    public void sendFlow(String phone, Send send) {
        SmsBo smsBo = new SmsBo();
        smsBo.setMessage(send.getInformation());
        smsBo.setMobile(phone);
        smsBo.setOrderId(UUID.randomUUID().toString());
        smsBo.setTemplate(SmsConstant.sendProductTemplate);
        sendMessage(smsBo);
    }

    @Override
    public void sendSuccess(String message,String phone) {
        SmsBo smsBo = new SmsBo();
        smsBo.setMessage(message);
        smsBo.setMobile(phone);
        smsBo.setOrderId(UUID.randomUUID().toString());
        smsBo.setTemplate(SmsConstant.willSendTemplate3);
        sendMessage(smsBo);
    }

    @Override
    public List<Send> getAllInformatin() {

        return sendMapper.selectAll();
    }

    /**
     * 发送短信请求方法
     *
     * @param smsRequestParam 商户传入的参数集合
     */
    private static void requestCharge(Map<String, String> smsRequestParam) {
//生成签名,组装参数
        String signKey = getSignKey(smsRequestParam);
        smsRequestParam.put("sign", signKey);
        NameValuePair[] requestParams = combineRequestParam(smsRequestParam);
//发起短信发送请求
        String response = postWithCharset(REQUEST_URL, requestParams, "UTF-8", 30 * 1000, 30 * 1000, true);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
//解析返回json字符串,成功时打印订单号,失败时打印返回错误码
            JsonNode jsonNode = objectMapper.readTree(response);
            if (StringUtils.equals(jsonNode.get(RETURN_CODE).asText(), SUCCESS)) {
                System.out.println("return_code:" + jsonNode.get(RETURN_CODE).asText());
                System.out.println("order_id:" + jsonNode.get("order_id").asText());
                return;
            }
            System.out.println("return_code:" + jsonNode.get(RETURN_CODE).asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成签名
     *
     * @param requestParams 请求参数
     * @return
     */
    private static String getSignKey(Map<String, String> requestParams) {
/**
 * mobile|account|time|tpl_id#tradeKey,加密字符串规则
 * tradeKey 是由您申请的交易密钥
 */
        StringBuffer source = new StringBuffer(50).append(requestParams.get("mobile")).append("|").append(requestParams.get(
                "account")).append("|").append(requestParams.get("time")).append("|").append(requestParams.get("tpl_id")).append("#").append(TRADE_KEY);
        return getMd5(source.toString(), true, "UTF-8").toUpperCase();
    }

    /**
     * 组装请求参数
     *
     * @param requestParams 请求参数
     * @return
     */
    private static NameValuePair[] combineRequestParam(Map<String, String> requestParams) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        requestParams.forEach((key, value) -> nameValuePairs.add(new NameValuePair(key, value)));
        return nameValuePairs.toArray(new NameValuePair[nameValuePairs.size()]);
    }

    /**
     * 发送Http请求工具
     *
     * @param requestUrl        请求地址
     * @param requestParams     请求数据
     * @param charset           编码格式
     * @param connectionTimeout 连接超时时间(毫秒)
     * @param soTimeout         读取超时时间(毫秒)
     * @param fag               是否启用多线程
     * @return
     */
    private static String postWithCharset(String requestUrl, NameValuePair[] requestParams, String charset, int connectionTimeout, int soTimeout, boolean fag) {
        String returnStr = "";
        PostMethod postMethod = null;
        try {
            HttpClient client;
            if (fag) {
                MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
                client = new HttpClient(connectionManager);
            } else {
                client = new HttpClient();
            }
            postMethod = new PostMethod(requestUrl);
            postMethod.setRequestBody(requestParams);
            client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            client.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
            client.executeMethod(postMethod);
            returnStr = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return returnStr;
    }

    /**
     * *
     * MD5工具类
     *
     * @param plainText 需要加密的字符串
     * @param md5Format true 32位，false 16 位
     * @param charset   字符集格式
     * @return
     */
    private static String getMd5(String plainText, boolean md5Format, String charset) {
        String md5Str = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(plainText.trim().getBytes(charset));
            byte[] hashBytes = messageDigest.digest();
            int i;
            StringBuffer tempStr = new StringBuffer(32);
            for (int offset = 0; offset < hashBytes.length; offset++) {
                i = hashBytes[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    tempStr.append("0");
                }
                tempStr.append(Integer.toHexString(i));
            }
            if (md5Format) {
                md5Str = tempStr.toString().toUpperCase();
            } else {
                md5Str = tempStr.toString().substring(8, 24).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }
}


