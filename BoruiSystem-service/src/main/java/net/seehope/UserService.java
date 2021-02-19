package net.seehope;

import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import net.seehope.pojo.Users;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    //查询用户的信息
    Users getUserInfo(String sno);


    //删除用户信息
    void deleteUser(String userId);

    //存用户信息
    void insertUser(Users user);

    Users login(Users user);

    //删除客户
    void deleteClient(String userId);

    //查询客户
    PageInfo getClients(Integer page,Integer pageSize);

    //导出客户Excel表
    ResponseEntity<byte[]> exportClientExcel(HttpServletRequest request, HttpServletResponse response, String excelName);

}
