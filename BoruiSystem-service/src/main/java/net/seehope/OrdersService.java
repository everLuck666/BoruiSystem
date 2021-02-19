package net.seehope;

import com.github.pagehelper.PageInfo;
import net.seehope.pojo.Invoices;
import net.seehope.pojo.bo.GetOrdersBo;
import net.seehope.pojo.bo.TotalStatisticBo;
import org.springframework.http.ResponseEntity;

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

    //得到累计收入
    String totalIncome();
}
