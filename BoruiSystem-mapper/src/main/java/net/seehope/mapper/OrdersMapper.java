package net.seehope.mapper;

import net.seehope.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
public interface OrdersMapper extends tk.mybatis.mapper.common.Mapper<Orders> {

    //得到订单表中的最小日期
    Date orderMinDate();


    //得到订单表中的最大日期
    Date orderMaxDate();

    List<Orders> getIncome(@Param("todays") Date todays, @Param("todaye")Date todaye);

}




