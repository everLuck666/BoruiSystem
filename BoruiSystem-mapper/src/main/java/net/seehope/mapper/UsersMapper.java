package net.seehope.mapper;

import net.seehope.pojo.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@Repository
public interface UsersMapper extends tk.mybatis.mapper.common.Mapper<Users> {

    //查找所有客户信息
    List queryUserInfos();
}




