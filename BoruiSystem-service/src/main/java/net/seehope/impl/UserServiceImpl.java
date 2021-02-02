package net.seehope.impl;

import net.seehope.UserService;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Users;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UsersMapper usersMapper;
    @Override
    public Users getUserInfo(String sno) {
        Users users = new Users();
        users.setSno(sno);

        return usersMapper.selectOne(users);
    }

    @Override
    public void deleteUser(String userId) {
        Users users = new Users();
        users.setSno(userId);
        usersMapper.delete(users);
    }

    @Override
    public void insertUser(Users user) {

            usersMapper.insert(user);


    }

    @Override
    public Users login(Users bo) {

        Users user = null;

        if (!StringUtils.isEmpty(bo.getSno()+"")) {
            Users temp = new Users();
            temp.setSno(bo.getSno());
            try{
                user = usersMapper.selectOne(temp);
            }catch (Exception e){
                throw new RuntimeException("找到了两个用户");
            }
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }
            if (!StringUtils.equals(bo.getPassword(), user.getPassword())) {
                throw new RuntimeException("密码错误");
            }
        }

        return user;
    }
}
