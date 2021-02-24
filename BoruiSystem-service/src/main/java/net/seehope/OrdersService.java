package net.seehope;

import com.github.pagehelper.PageInfo;
import net.seehope.pojo.Invoices;
import net.seehope.pojo.bo.GetOrdersBo;
import net.seehope.pojo.bo.TotalStatisticBo;
import org.springframework.http.ResponseEntity;
import net.seehope.pojo.bo.PayBo;
import net.seehope.pojo.vo.OrderVo;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OrdersService {
    //获取待处理订单数量
    Integer getWaitingOrders();

    //获取已完成订单数量
    Integer getFinishedOrders();

    //销量统计
    List salesStatistic();

    //销量统计合计数据
    TotalStatisticBo totalStatistic();

    //根据筛选条件获取所有订单信息，分页返回
    PageInfo getAllOrders(GetOrdersBo ordersBo);

    //将订单状态修改为发货
    void updateOrder(String orderId);

    //查看发票
    Invoices getInvoices(String orderId);

    //导出订单信息到Excel
    ResponseEntity<byte[]> exportOrderExcel(HttpServletRequest request, HttpServletResponse response, String excelName);

    //得到今日收入
    String getTodaySales();

    //得到本月收入
    String getMonthSales();


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
//形成预处理订单,返回需要付钱的金额
    String[] pretreatment(PayBo payBo);
    //返回根据商品和商品的数量返回价格
    int getPrice(String goodName,String num);

    //把预处理生成的订单改成已经支付完成的状态
    void finishOrder(String orderId);

    //判断订单是否已经完成了支付状态
    boolean isOrderFinish(String orderId);
}
