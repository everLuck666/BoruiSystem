package net.seehope;

import net.seehope.pojo.Users;

public interface UserService {

    //查询用户的信息
    Users getUserInfo(String sno);


    //删除用户信息
    void deleteUser(String userId);

    //存用户信息
    void insertUser(Users user);

    Users login(Users user);
}
