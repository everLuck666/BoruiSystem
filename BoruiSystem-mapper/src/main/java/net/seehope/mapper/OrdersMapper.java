package net.seehope.mapper;

import net.seehope.pojo.Orders;
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

}




