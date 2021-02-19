package net.seehope.impl;

import net.seehope.UserService;
import net.seehope.common.UserType;
import net.seehope.exception.PassPortException;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Users;
import net.seehope.pojo.bo.ManagerBo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UsersMapper usersMapper;


    @Override
    public Users getUserInfo(String sno) {
        Users users = new Users();
        users.setUserId(sno);
        Users userValue = usersMapper.selectOne(users);
        if(userValue != null){
            return userValue;
        }
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        Users user = new Users();
        user.setUserId(userId);

        if (isExist(user.getUserId())){
            Users userTemp = new Users();
            userTemp.setIdentity(UserType.SUPERMANAGER.getType());
            List list = usersMapper.select(userTemp);
            if(list.size() == 1){
                throw new RuntimeException("还剩一个管理员，禁止删除");
            }
            usersMapper.delete(user);
        }else{
            throw new RuntimeException("账号不存在");
        }


    }

    @Override
    public void insertUser(Users user,int identity) {
        if (isExist(user.getUserId())){
            throw new RuntimeException("账号存在");
        }else{
            user.setIdentity(identity);
            user.setVersion("0");
            usersMapper.insert(user);
        }


    }

    @Override
    public Users login(ManagerBo bo) {
        Users user = null;

        if (!StringUtils.isEmpty(bo.getUsername())) {
            Users temp = new Users();
            temp.setUserId(bo.getUsername());
            try{
                user = usersMapper.selectOne(temp);
            }catch (Exception e){
                throw new RuntimeException("找到了两个用户");
            }
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }
            if (!StringUtils.equals(bo.getPassword(), user.getPassword())) {
                throw new PassPortException("密码错误");
            }
        }

        return user;
    }

    @Override
    public boolean isExist(String userId) {
        Users users = new Users();
        users.setUserId(userId);

        Users users1 =  usersMapper.selectOne(users);
        if(users1 != null){
            return true;
        }

        return false;
    }

    @Override
    public List<Users> getAllManagers() {
        Users users = new Users();
        users.setIdentity(UserType.SUPERMANAGER.getType());
        return usersMapper.select(users);
    }

    @Override
    public void updateVersion(String version,String userId) {
        usersMapper.updateVersion(version,userId);

    }
}

