package com.github.wxpay.service;

import net.seehope.pojo.bo.WeChatPayBo;

import javax.servlet.http.HttpServletResponse;

public interface AlipayService {

    void alipay(HttpServletResponse res, WeChatPayBo weChatPayBo);
}
