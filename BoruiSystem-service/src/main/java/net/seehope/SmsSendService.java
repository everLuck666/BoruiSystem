package net.seehope;

import net.seehope.pojo.Send;
import net.seehope.pojo.bo.SmsBo;

import java.util.List;

public interface SmsSendService {
    //发送短信
    void sendMessage(SmsBo bo);
    //发送给所有的客户
    void sendAllPeople(String message,String managerName);
    //发送给订阅的客户
    void sendPeopel(String message,String managerName);
    //发送物流短信
    void sendFlow(String phone, Send send);
    //发送付款成功短信
    void sendSuccess(String message,String phone);

    //得到所有的通知记录
    List<Send> getAllInformatin();
}
