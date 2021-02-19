package net.seehope;

import net.seehope.pojo.bo.PayBo;
import net.seehope.pojo.vo.OrderVo;

import java.util.Date;
import java.util.List;

public interface OrdersService {
    //得到今日收入

    String getTodayIncome();



    //得到本月收入
    String getMonthIncome();


    //得到累计收入
    String totalIncome();
    //得到多天的每日收入
    List<OrderVo> getAllIncome();

    //得到这一天的收入
    String getTodayIncome(Date date);
//形成预处理订单
    void pretreatment(PayBo payBo);
    //返回根据商品和商品的数量返回价格
    Double getPrice(String goodName,String num);
}
