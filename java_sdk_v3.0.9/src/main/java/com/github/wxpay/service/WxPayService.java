package com.github.wxpay.service;

import com.github.wxpay.bo.UserOrderInformationBo;
import net.seehope.pojo.bo.WeChatPayBo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface WxPayService {
    public Map<String,String>wxPay(String openId, String ipAddress) throws Exception;
    void doWx(HttpServletRequest request, HttpServletResponse response, WeChatPayBo weChatPayBo) throws Exception;
}
