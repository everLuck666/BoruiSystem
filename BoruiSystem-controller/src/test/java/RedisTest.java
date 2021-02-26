import net.seehope.Application;
import net.seehope.SmsSendService;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Users;
import net.seehope.util.SerializeUtil;
import net.seehope.util.SmsUtils;
import org.bouncycastle.crypto.tls.UserMappingType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTest {

//    private static final Object JSON = ;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    SmsSendService smsSendService;
    @Autowired
    UsersMapper usersMapper;
   @Test
    public void test(){
        redisTemplate.opsForValue().set("123", "23");
    }

    @Test
    public void smsRest(){
        String message =  SmsUtils.connect("code1","小说～销售","code2","2123");
        smsSendService.sendSuccess(message,"13060961170");
    }

    //发送物流测试
    @Test
    public void flow(){
        String deliveryId = "12345";
        String phone = "13060961170";
        String productName = "小说";
        String deliveryName = "韵达";

        String message = SmsUtils.connect("code1",productName,"code2",deliveryName,"code3",deliveryId);

        smsSendService.sendFlow(message,phone);
    }
    //测试发送订阅短信
    @Test
    public void send(){
        List<String> usersList = usersMapper.getAllPeoplePhone("订阅");
        String message = SmsUtils.connect("code1","你好呀");
        smsSendService.sendAllPeople(message);
    }


    @Test
    public void textRrdis() {
//
        //设置user对象
        Users user =new Users();
       user.setUserName("lala");

        //把user对象序列化后存储进redis
        redisTemplate.opsForValue().set("123", user);

        //从redis取出user后，把user对象反序列化
        Users user2=(Users) redisTemplate.opsForValue().get("123");
       System.out.println(user2.getUserName());
    }



}
