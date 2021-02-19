package net.seehope.controller;

import net.seehope.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class LoginController {
    @Autowired
    UserService userService;


  //  @PostMapping(value = "manager",produces="application/json;charset=UTF-8")

//    public RestfulJson loginManger(@RequestBody Users bo, HttpServletRequest request){
//
//        Users usrs = userService.getUserInfo(bo.getSno());
//        int version = Integer.valueOf(usrs.getVersion());
//        version++;
//        if(version == 1000){
//            version = 0;
//        }
//        usrs.setVersion(version);
//        userService.deleteUser(bo.getSno());
//        userService.insertUser(usrs);//版本号+1
//
//        String message = request.getHeader("agent");
//        boolean flag = false;
//        if(StringUtils.equals(message,"web")){
//            flag = true;
//        }
//        Map<String,String> map = new HashMap();
//
//        try {
//            Users users =  userService.login(bo);
//            if(users.getIdentity()==-1){
//                return RestfulJson.errorMsg("管理员已经被删除");
//            }
//            Map<String, String> payload = new HashMap<>();
//
//            payload.put("sno", users.getSno()+"");
//            payload.put("version",users.getVersion()+"");
//            payload.put("identity", users.getIdentity() + "");
//            String token = JWTUtils.getToken(payload);
//            map.put("token", token);
//
//            Calendar calendar = new GregorianCalendar();
//            calendar.setTime(new Date());
//            calendar.add(Calendar.DAY_OF_MONTH, +7);
//            Date cd = calendar.getTime();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String time = simpleDateFormat.format(cd);
//            map.put("expired", time);
//            map.put("identity", users.getIdentity() + "");
//        } catch (PassPortException e){
//            return RestfulJson.errorMsg("密码错误");
//        }catch (Exception e){
//            return RestfulJson.errorMsg("管理员不存在或者找到了两个用户");
//        }
//        return RestfulJson.isOk(map);
//    }
}
