package net.seehope.mapper;

import net.seehope.pojo.Invoices;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@Repository
@CacheNamespace
public interface InvoicesMapper extends tk.mybatis.mapper.common.Mapper<Invoices> {

}




