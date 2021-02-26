package com.github.wxpay.service;



import com.alipay.api.response.AlipayTradeQueryResponse;
import net.seehope.pojo.bo.WeChatPayBo;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AlipayService {

    void alipay(HttpServletResponse res, WeChatPayBo weChatPayBo);

    void aliNotify(Map<String, String> param) throws Exception;
//
   AlipayTradeQueryResponse queryOrder(String orderId) throws Exception;
}
