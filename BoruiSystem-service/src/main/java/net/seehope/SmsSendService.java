package net.seehope;

import net.seehope.pojo.bo.SmsBo;

public interface SmsSendService {
    //发送短信
    void sendMessage(SmsBo bo);
    //发送给所有的客户
    void sendAllPeople(String message);
    //发送给订阅的客户
    void sendPeopel(String message);
    //发送物流短信
    void sendFlow(String message,String phone);
    //发送付款成功短信
    void sendSuccess(String message,String phone);
}
