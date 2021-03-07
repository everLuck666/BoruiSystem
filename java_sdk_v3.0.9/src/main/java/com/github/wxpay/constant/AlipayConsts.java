package com.github.wxpay.constant;
public class AlipayConsts {
    public final static String AliTradeSuccess = "TRADE_SUCCESS";
    public final static String AliTradeFinished = "TRADE_FINISHED";

    public final static String AliOutTradeNo = "out_trade_no";
    public final static String TradeStatus = "trade_status";

    public final static String SuccessCode = "10000";


                                                   //支付宝支付成功的回调地址
    public final  static  String SUCCESS_NOTIFY2 = "http://8.129.181.245:8888/wxPay/alinotify";

    public static class Status {
        public final static int MachineOrderInit = 0;        //未支付
        public final static int MachineOrderPaySucc = 1;    //充值成功
    }

}
