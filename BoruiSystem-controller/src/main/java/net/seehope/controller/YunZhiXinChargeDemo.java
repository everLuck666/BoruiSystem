package net.seehope.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 短信发送示例代码
 *
 * @author www.yunzhixin.com
 * @date 2017/11/10
 */
public class YunZhiXinChargeDemo {
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

    public static void main(String[] args) {
//组装请求参数
        Map<String, String> smsRequestParam = new HashMap<>(16);
//用户编号，注册www.yunzhixin.com的手机号码
        smsRequestParam.put("account", "18555477301");
//需要发送的手机号
        smsRequestParam.put("mobile", "13060961170");
//商户提交的订单号（商户保证其唯一性）
        smsRequestParam.put("order_id", "123434");
//用户服务器时间戳(13位),JDK8版本可以使用String.valueOf(Instant.now().toEpochMilli())获取,JDK7版本可以使用String.valueOf(System.currentTimeMillis())获取
        smsRequestParam.put("time", String.valueOf(Instant.now().toEpochMilli()));
//模板编号
        smsRequestParam.put("tpl_id", "TP2102193");
//短信所需传入的参数
        smsRequestParam.put("params", "code1:1234,code2:123,code3:123");
        requestCharge(smsRequestParam);
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