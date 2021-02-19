package net.seehope.mapper;

import net.seehope.pojo.Users;
import org.apache.ibatis.annotations.Param;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
public interface UsersMapper extends tk.mybatis.mapper.common.Mapper<Users> {
    //更新版本号
    void updateVersion(@Param("version")String version, @Param("userId")String userId);
}




