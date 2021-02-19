package net.seehope.mapper;

import net.seehope.pojo.CountPeople;

import java.util.Date;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
public interface CountPeopleMapper extends tk.mybatis.mapper.common.Mapper<CountPeople> {

    //得到订单表中的最小日期
    Date countMinDate();


    //得到订单表中的最大日期
    Date countMaxDate();




}




