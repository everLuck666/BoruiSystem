package net.seehope.mapper;

import net.seehope.pojo.Goods;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@Repository
@CacheNamespace
public interface GoodsMapper extends tk.mybatis.mapper.common.Mapper<Goods> {
}




