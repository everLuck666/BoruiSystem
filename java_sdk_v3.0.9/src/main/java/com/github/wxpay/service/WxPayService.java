package com.github.wxpay.service;

import com.github.wxpay.bo.UserOrderInformationBo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface WxPayService {
    public Map<String,String>wxPay(String openId, String ipAddress) throws Exception;
    void doWx(HttpServletRequest request, HttpServletResponse response, String ipAddress,int totalPrice,String orderId,String body) throws Exception;
}
