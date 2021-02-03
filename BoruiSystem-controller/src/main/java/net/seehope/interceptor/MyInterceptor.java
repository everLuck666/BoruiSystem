package net.seehope.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.seehope.UserService;
import net.seehope.jwt.JWTUtils;
import net.seehope.pojo.Users;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class MyInterceptor implements HandlerInterceptor {

    UserService userService;


    private final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    String[] value = new String[1];
    String message = null;

    public MyInterceptor(String[] value, String message, UserService userService) {
        this.value = value;
        this.message = message;
        this.userService = userService;

    }


//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        PrintWriter out = null;
//        logger.info("request请求地址path：" + request.getServletPath());
//        //从请求头获取token
//        String token = request.getHeader("token");
//
//
//        JSONObject jsonObject = new JSONObject();
//
//
//        try {
//            logger.info("token是" + token);
//
//            JWTUtils.verify(token);
//            DecodedJWT verify = JWTUtils.getTokenInfo(token);
//            Users users = userService.getUserInfo(verify.getClaim("sno").asString());
//            logger.info("这个用户的信息是"+users.getIdentity());
//            logger.info("这个用户的版本号是"+users.getVersion());
//            if (users == null) {
//                logger.info("这个用户不存在");
//                jsonObject.put("state", false);
//                jsonObject.put("msg", "用户请先登录或者管理员不存在");
//
//            } else if (!StringUtils.equals(users.getIdentity() + "", verify.getClaim("identity").asString())) {
//                jsonObject.put("state", false);
//                jsonObject.put("msg", "token身份和数据库中的身份不符合");
//                logger.info("和数据库的身份不符合");
//                logger.info("用户身份是"+users.getIdentity() + "");
//                logger.info("token身份是"+verify.getClaim("identity").asString());
//
//            } else if(!StringUtils.equals(users.getVersion() + "", verify.getClaim("version").asString())){
//                jsonObject.put("state", false);
//                jsonObject.put("msg", "这个账号在别的地方登陆，请重新登录");
//                logger.info("这个账号在别的地方登陆，请重新登录");
//            }else{
//                logger.info("长度是" + value.length);
//                for (int i = 0; i < value.length; i++) {
//                    logger.info("登录的人的身份是" + verify.getClaim("identity").asString());
//                    logger.info("我是指" + value[i].equals(verify.getClaim("identity").asString()));
//                    if (value[i].equals(verify.getClaim("identity").asString())) {
//                        logger.info("拦截器拦截是的是" + verify.getClaim("sno").asString());
//                        request.setAttribute("sno", verify.getClaim("sno").asString());
//                        return true;
//                    }
//                }
//                jsonObject.put("state", false);
//                jsonObject.put("msg", message);
//            }
//
//        } catch (TokenExpiredException e) {
//            jsonObject.put("state", false);
//            jsonObject.put("msg", "token过期");
//
//        } catch (SignatureVerificationException e) {
//            jsonObject.put("state", false);
//            jsonObject.put("msg", "签名错误！！");
//        } catch (AlgorithmMismatchException e) {
//            jsonObject.put("state", false);
//            jsonObject.put("msg", "加密算法错误");
//        } catch (Exception e) {
//            jsonObject.put("state", false);
//            jsonObject.put("msg", "无效token");
//        }
//
//
//        out = response.getWriter();
//        out.write(jsonObject.toJSONString());
//        out.flush();
//        out.close();

        //return false;

    }


//}
