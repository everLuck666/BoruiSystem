import net.seehope.Application;
import net.seehope.SmsSendService;
import net.seehope.UserService;
import net.seehope.common.SmsConstant;
import net.seehope.mapper.SendMapper;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Users;
import net.seehope.pojo.bo.SmsBo;
import net.seehope.pojo.bo.StoreSendBo;
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
import java.util.UUID;

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

    @Autowired
    SendMapper sendMapper;
    @Autowired
    UserService userService;
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

       // smsSendService.sendFlow(message,phone);
    }
    //测试发送订阅短信
    @Test
    public void send(){
        List usersList = usersMapper.getAllPeoplePhone("订阅");
//        String message = SmsUtils.connect("code1","你好呀");
//        smsSendService.sendAllPeople(message,"123");

        SmsBo smsBo = new SmsBo();
        smsBo.setMessage("code1:nihao");
        smsBo.setMobile("13025179492");
        smsBo.setOrderId(UUID.randomUUID().toString());
        smsBo.setTemplate(SmsConstant.newProductTemplate);
        smsSendService.sendMessage(smsBo);
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

   @Test
    //测试短信存储
    public void storeMessageTest(){
        System.out.println(sendMapper.selectAll());
    }

    @Test
    public void ts(){
        List<StoreSendBo> usersList = usersMapper.getAllPeoplePhone("");
        for (StoreSendBo storeSendBo:usersList){
            System.out.println(storeSendBo.getPhone());
            System.out.println(storeSendBo.getUserName());
        }

    }


    //得到所有的通知记录
@Test
    public void getAllInformationTest(){
        smsSendService.getAllInformatin();
    smsSendService.getAllInformatin();
    }

    //测试mybatis二级缓存
    @Test
    public void mybatisTest(){
        userService.updateVersion(2+"","123456");//版本号+1

        Users users = userService.getUserInfo("123456");
        System.out.println("----------------------------");
        userService.updateVersion(3+"","123456");//版本号+1

        Users user2s = userService.getUserInfo("123456");
        System.out.println("------------------");
        System.out.println(user2s.getVersion());



    }


}
