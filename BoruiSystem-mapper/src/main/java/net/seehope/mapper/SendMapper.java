package net.seehope.mapper;

import net.seehope.pojo.Orders;
import net.seehope.pojo.Send;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheNamespace
public interface SendMapper  extends tk.mybatis.mapper.common.Mapper<Send>  {


}
