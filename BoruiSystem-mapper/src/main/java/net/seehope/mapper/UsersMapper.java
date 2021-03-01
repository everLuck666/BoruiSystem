package net.seehope.mapper;

import net.seehope.pojo.Users;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@Repository
public interface UsersMapper extends tk.mybatis.mapper.common.Mapper<Users> {
    //更新版本号
    void updateVersion(@Param("version")String version, @Param("userId")String userId);

    //查找所有客户信息
    List queryUserInfos();

    //删除客户
    void deleteUser(String userId);

    List<String> getAllPeoplePhone(@Param("subscribe") String  subscribe);
}




