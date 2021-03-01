package com.github.wxpay.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.github.wxpay.constant.AlipayConsts;
import com.github.wxpay.constant.WechatConstant;
import com.github.wxpay.service.AlipayService;
import com.github.wxpay.util.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;
import net.seehope.common.RetEnum;
import net.seehope.exception.ApiException;
import net.seehope.pojo.bo.WeChatPayBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
    Logger logger = LoggerFactory.getLogger("AlipayServiceImpl");
    @Override
    public  void alipay(HttpServletResponse res, WeChatPayBo weChatPayBo){
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());

        List<Object> goodsDetailList = new ArrayList<>();
        Map<String, Object> optionalArgs = new HashMap<>();
        optionalArgs.put("productName", weChatPayBo.getProductNames());
        optionalArgs.put("orderId", weChatPayBo.getOrderId());
        optionalArgs.put("userId",weChatPayBo.getUserId());
        optionalArgs.put("phone",weChatPayBo.getPhone());
        goodsDetailList.add(optionalArgs);

        try {
            double price = weChatPayBo.getTotalPrice()/100.0;
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().optional("goodsDetails",goodsDetailList)
                    .preCreate(weChatPayBo.getProductNames(), weChatPayBo.getOrderId(), price+"");
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
                String codeUrl = response.getQrCode();
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
                MatrixToImageWriter.writeToStream(bitMatrix, format, res.getOutputStream());
                logger.info(response.getQrCode());
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";

        config.appId = "2021002128658398";

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCiqnFJEGfujxUddcQScOo/A0vrVV1xmWaZjraWrlSjUAidxm1DuY6Aa1usev/4s3n6PD3FlClGaPbvpsBoyNYsVrRuwdxKs2rzL5cKsTab4VH4qDzey5t9ymWrk3LwBsNBWIct7+i11bEQqCt40MDDuGyj2leK93nYJBA6IFLQX5rDRtAH5kKfK3a5FQQjMfBcgDQFzg3Y4fQ69VwIWtZ5gX00IC57IrAioI7YwFF9tkMFncVjJnbExG68QrMw2UiLj7ePSbzGv17LPyCxLPJNa38QHpGGjuKvF5XgkjddmwBG645cCbVj51NqzeaRL2urzdk2E94S9PEv/UmVqBebAgMBAAECggEBAJHNgsOB4BbVy5BfecMp0N1wYAFtv9dOL8feQs1nL1g/Kan6LQAyd4emM0Kz8XKDtQbWLmxF9IU3GngPGJITyt1tTkeknoeasHeQJaug0pkAaxAeBImTKSmkXGNhQ/nfpe6RooFLdGRgSVjr1CNzwgTs7vwjfqqCToWtNZpUVMaOKzc3e7WT/A5cI8h1/SRm24MngZiK8Q5286xv69/iCttIbVYKeUO7fZExNnoOh6f75cbN8WFo/jg1EO3CcqNfLffUNB3Yrg8SweF1f9xpYFr529hHrmywp194uibZurR3/A2rFNuMtc3QjCwLMBY2zbPj60VtfuzXD8xDPlCQh9ECgYEA5C42XND+YRtU412NEdDYrcgkZAJw11iZizmYLEATQpOgSK3iu8F6tlmVC+cj7MSP6tFtjC0vFLD5vOHTjw6rUHlQNlCblp9A2YWs6RBFSEgdxZeDC3M2j7EDYcD8qSQBhFhAAgVy1MIknkXgbRFfV4tiCCYTcE8zrUpNeKduMZkCgYEAtn9v3jg+uUSGWWTbq0+vmtZF1h9vj8op1YaUgD6L49WwSeXtwLA2qJkJ39UpfhjxtceHL5Y9q4VwBJgoFBM//fnuC8aRIBolhby0Bma+E7wWvsP0VyJhMT+LOcTO6+GVQ2k3LNXD8i3axbgo8nu+MaLBqdxwccOJf3b/pa7V+1MCgYACK0pLAZWZeLgK3UXNCI42wLNP3BIUifwTrb17ljRYqqyYZEbGgCVZfGqFTXIy+v9fPRRsg7Vx3ejR2de4AWyxfCW+DnAcM3FbYADvyj7OXBkkWrddMYAwR39/u2TrerMk/e3F1yVrTlkoxANJJiQg4etRpoMCy03zdMRdxEGw6QKBgDGJQ9dA0x280O7g5pOIjLTtpoUpgUG5cUOJRTPORnBwZ2qXo8Ji0mNPLxP3q0yT/sAFydcL4+9zx+UnW/GfDAanWYsOugPJtRepEgCO0NdQfagIToF8Tn9srSDgH/58++QRYi2kVIyfD7wNcefn2MdmU0UFFex7VA4qQdg6LdELAoGBALwH/00wKGcZu33nld7OrQPO6sbDvEN2HtTX4ZCWqT2IDDYPM543epW07wXfkVTPyscvUrHIlFHWPBMet4BGMZdDdWJZFk89I+J0tf6b7HAoxkm5RlFtxrn+9hr0EQGuWVllmHiLUrZFj/QYndQxA5UeueV/iS+VE0gz6yzntuQ9";

        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载


        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArfd2HmjRDUjQLwYPp62kVi7wAoYViHCyurtNN5kd5vyEKvdATdz6JX5M5hUszacQVo7sydLWap1z0hyhg7qBU2qxfGS8Ge6/cX09RivT4WXRfC+0R7EFoNCUoKtwpAgrb5WWlG39v2wS9owTrntZCOgUS6FE8wLGiSJ/9hP3v9XuMbhEO+pRl6r4N+d9B9k2vmxuanNSnPv+3PjQQ8S5OM9S0bt5wMKH23QOnbKZQkgcljuQHephuI1p+OG2iAdH1Ku+Ub8221mP/5TIyPwgrzQGLefJCSgYkdWrRH6WjNSj7IGqP9CLM4G+vlEdlhJa6JmCCyUc6Vb09g9BvTwj+QIDAQAB";

        //可设置异步通知接收服务地址（可选）
//        config.notifyUrl = "<-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->";
        config.notifyUrl = AlipayConsts.SUCCESS_NOTIFY2;
        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";

        return config;
    }

    @Override
    public void aliNotify(Map<String, String> param) throws Exception {
        log.info("支付宝异步回调接口数据处理");
        //只有支付成功后，支付宝才会回调应用接口，可直接获取支付宝响应的参数
        String order_id = param.get(AlipayConsts.AliOutTradeNo);
        //出于安全考虑，通过支付宝回传的订单号查询支付宝交易信息
        AlipayTradeQueryResponse aliResp = queryOrder(order_id);
        if (!AlipayConsts.SuccessCode.equals(aliResp.getCode())) {
            //返回值非10000
            throw new ApiException(RetEnum.MachineOrderAlipayException, aliResp.getSubMsg());
        }
        if (!AlipayConsts.AliTradeSuccess.equals(aliResp.getTradeStatus()) && !AlipayConsts.AliTradeFinished.equals(aliResp.getTradeStatus())) {
            //支付宝订单状态不是支付成功
            throw new ApiException(RetEnum.MachineOrderAliUnPay);
        }
        //可对支付宝响应参数AlipayTradeQueryResponse进行处理

    }

    @Override
    public com.alipay.api.response.AlipayTradeQueryResponse queryOrder(String orderId) throws Exception {
        String aliAppId = "2021002128658398";
        String aliUrl = "openapi.alipay.com";
        String aliAppPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCiqnFJEGfujxUddcQScOo/A0vrVV1xmWaZjraWrlSjUAidxm1DuY6Aa1usev/4s3n6PD3FlClGaPbvpsBoyNYsVrRuwdxKs2rzL5cKsTab4VH4qDzey5t9ymWrk3LwBsNBWIct7+i11bEQqCt40MDDuGyj2leK93nYJBA6IFLQX5rDRtAH5kKfK3a5FQQjMfBcgDQFzg3Y4fQ69VwIWtZ5gX00IC57IrAioI7YwFF9tkMFncVjJnbExG68QrMw2UiLj7ePSbzGv17LPyCxLPJNa38QHpGGjuKvF5XgkjddmwBG645cCbVj51NqzeaRL2urzdk2E94S9PEv/UmVqBebAgMBAAECggEBAJHNgsOB4BbVy5BfecMp0N1wYAFtv9dOL8feQs1nL1g/Kan6LQAyd4emM0Kz8XKDtQbWLmxF9IU3GngPGJITyt1tTkeknoeasHeQJaug0pkAaxAeBImTKSmkXGNhQ/nfpe6RooFLdGRgSVjr1CNzwgTs7vwjfqqCToWtNZpUVMaOKzc3e7WT/A5cI8h1/SRm24MngZiK8Q5286xv69/iCttIbVYKeUO7fZExNnoOh6f75cbN8WFo/jg1EO3CcqNfLffUNB3Yrg8SweF1f9xpYFr529hHrmywp194uibZurR3/A2rFNuMtc3QjCwLMBY2zbPj60VtfuzXD8xDPlCQh9ECgYEA5C42XND+YRtU412NEdDYrcgkZAJw11iZizmYLEATQpOgSK3iu8F6tlmVC+cj7MSP6tFtjC0vFLD5vOHTjw6rUHlQNlCblp9A2YWs6RBFSEgdxZeDC3M2j7EDYcD8qSQBhFhAAgVy1MIknkXgbRFfV4tiCCYTcE8zrUpNeKduMZkCgYEAtn9v3jg+uUSGWWTbq0+vmtZF1h9vj8op1YaUgD6L49WwSeXtwLA2qJkJ39UpfhjxtceHL5Y9q4VwBJgoFBM//fnuC8aRIBolhby0Bma+E7wWvsP0VyJhMT+LOcTO6+GVQ2k3LNXD8i3axbgo8nu+MaLBqdxwccOJf3b/pa7V+1MCgYACK0pLAZWZeLgK3UXNCI42wLNP3BIUifwTrb17ljRYqqyYZEbGgCVZfGqFTXIy+v9fPRRsg7Vx3ejR2de4AWyxfCW+DnAcM3FbYADvyj7OXBkkWrddMYAwR39/u2TrerMk/e3F1yVrTlkoxANJJiQg4etRpoMCy03zdMRdxEGw6QKBgDGJQ9dA0x280O7g5pOIjLTtpoUpgUG5cUOJRTPORnBwZ2qXo8Ji0mNPLxP3q0yT/sAFydcL4+9zx+UnW/GfDAanWYsOugPJtRepEgCO0NdQfagIToF8Tn9srSDgH/58++QRYi2kVIyfD7wNcefn2MdmU0UFFex7VA4qQdg6LdELAoGBALwH/00wKGcZu33nld7OrQPO6sbDvEN2HtTX4ZCWqT2IDDYPM543epW07wXfkVTPyscvUrHIlFHWPBMet4BGMZdDdWJZFk89I+J0tf6b7HAoxkm5RlFtxrn+9hr0EQGuWVllmHiLUrZFj/QYndQxA5UeueV/iS+VE0gz6yzntuQ9";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArfd2HmjRDUjQLwYPp62kVi7wAoYViHCyurtNN5kd5vyEKvdATdz6JX5M5hUszacQVo7sydLWap1z0hyhg7qBU2qxfGS8Ge6/cX09RivT4WXRfC+0R7EFoNCUoKtwpAgrb5WWlG39v2wS9owTrntZCOgUS6FE8wLGiSJ/9hP3v9XuMbhEO+pRl6r4N+d9B9k2vmxuanNSnPv+3PjQQ8S5OM9S0bt5wMKH23QOnbKZQkgcljuQHephuI1p+OG2iAdH1Ku+Ub8221mP/5TIyPwgrzQGLefJCSgYkdWrRH6WjNSj7IGqP9CLM4G+vlEdlhJa6JmCCyUc6Vb09g9BvTwj+QIDAQAB";

        log.info("查询支付宝订单，订单编号为：{}", orderId);
        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, aliAppId, aliAppPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderId);
        request.setBizModel(model);
        com.alipay.api.response.AlipayTradeQueryResponse response = alipayClient.execute(request);
        log.info("查询支付宝订单，返回数据：{}", response);
        return response;
    }





}


