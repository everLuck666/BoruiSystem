package net.seehope.mapper;

import net.seehope.pojo.Orders;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@Repository
@CacheNamespace
public interface OrdersMapper extends tk.mybatis.mapper.common.Mapper<Orders> {

    //返回待处理订单数量
    Integer queryWaitingOrders();

    //返回已完成订单数量
    Integer queryFinishedOrders();

    //返回所有订单信息
    List queryAllOrders();

    //按筛选条件返回所有订单信息
    List queryOrdersBy(String status,String orderId);
    //得到订单表中的最小日期
    Date orderMinDate();


    //得到订单表中的最大日期
    Date orderMaxDate();

    List<Orders> getIncome(@Param("todays") Date todays, @Param("todaye")Date todaye);

    //查找商品名称和商品单价
    List getProductInfo();

    //根据商品名称查找今日/本月销量
    Integer querySales(String productName,@Param("todays") Date todays, @Param("todaye")Date todaye);

    //根据商品名称查找累计销量
    Integer queryTotalSales(String productName);

    //查找合计部分今日售出/本月售出数据
    Integer queryTotalStatistics(@Param("todays") Date todays, @Param("todaye")Date todaye);

    //合计部分累计售出数据
    Integer queryTotal();

}




