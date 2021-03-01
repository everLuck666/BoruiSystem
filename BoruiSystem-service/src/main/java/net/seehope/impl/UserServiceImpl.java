package net.seehope.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.seehope.UserService;
import net.seehope.common.UserType;
import net.seehope.exception.PassPortException;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Users;
import net.seehope.pojo.bo.ManagerBo;
import net.seehope.pojo.bo.UsersInfoBo;
import net.seehope.util.ExcelFormatUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UsersMapper usersMapper;


    @Override
    public Users getUserInfo(String sno) {
        Users users = new Users();
        users.setUserId(sno);
        Users userValue = usersMapper.selectOne(users);
        if (userValue != null) {
            return userValue;
        }
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        Users user = new Users();
        user.setUserId(userId);

        if (isExist(user.getUserId())) {
            Users userTemp = new Users();
            userTemp.setIdentity(UserType.SUPERMANAGER.getType());
            List list = usersMapper.select(userTemp);
            if (list.size() == 1) {
                throw new RuntimeException("还剩一个管理员，禁止删除");
            }
            usersMapper.delete(user);
        } else {
            throw new RuntimeException("账号不存在");
        }


    }

    @Override
    public void insertUser(Users user, int identity) {
        if (isExist(user.getUserId())) {
            throw new RuntimeException("账号存在");
        } else {
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
    public void deleteClient(String userId) {

        usersMapper.deleteUser(userId);
    }

    @Override
    public PageInfo getClients(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List clients = usersMapper.queryUserInfos();
        PageInfo pageInfo = new PageInfo(clients);
        return pageInfo;
    }

    @Override
    public ResponseEntity<byte[]> exportClientExcel(HttpServletRequest request, HttpServletResponse response, String excelName) {
        try {
            logger.info(">>>>>>>>>>开始导出excel>>>>>>>>>>");


            List<UsersInfoBo> list = usersMapper.queryUserInfos();

            BaseFrontServiceImpl baseFrontController = new BaseFrontServiceImpl();
            return baseFrontController.buildResponseEntity(export(list), excelName + ".xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>导出excel 异常，原因为：" + e.getMessage());
        }
        return null;
    }

    private InputStream export (List < UsersInfoBo > list) {
        logger.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
        Map<String, String> map = new HashMap();
        map.put("0", "否");
        map.put("1", "是");
        ByteArrayOutputStream output = null;
        InputStream inputStream1 = null;
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = ExcelFormatUtil.headSytle(wb);// cell样式
        CellStyle content = ExcelFormatUtil.contentStyle(wb);// 报表体样式

        // 每一列字段名
        String[] strs = new String[]{"订阅", "客户姓名", "联系方式", "邮箱", "收货地址",
                "购买商品", "订单总额"};

        // 字段名所在表格的宽度
        int[] ints = new int[]{5000, 5000, 5000, 5000, 5000, 5000, 5000};

        // 设置表头样式
        ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
        logger.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");

        if (list != null && list.size() > 0) {
            logger.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
            for (int i = 0; i < list.size(); i++) {
                UsersInfoBo user = list.get(i);
                SXSSFRow row = sheet.createRow(i + 1);
                int j = 0;

                SXSSFCell cell = row.createCell(j++);
                cell.setCellValue(map.get(user.getSubscribeStatus()));
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getUserName());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getPhone());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getEmail());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getAddress());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getProductName());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getOrderAmout());
                cell.setCellStyle(content);


            }
            logger.info(">>>>>>>>>>>>>>>>>>>>结束遍历数据组装单元格内容>>>>>>>>>>");
        }
        try {
            output = new ByteArrayOutputStream();
            wb.write(output);
            inputStream1 = new ByteArrayInputStream(output.toByteArray());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                    if (inputStream1 != null)
                        inputStream1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream1;
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
