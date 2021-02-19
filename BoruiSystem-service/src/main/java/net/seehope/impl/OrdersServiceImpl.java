package net.seehope.impl;

import com.sun.tools.corba.se.idl.constExpr.Or;
import io.swagger.models.auth.In;
import net.seehope.IndexService;
import net.seehope.OrdersService;
import net.seehope.common.SendStatus;
import net.seehope.common.UserType;
import net.seehope.mapper.GoodsMapper;
import net.seehope.mapper.InvoicesMapper;
import net.seehope.mapper.OrdersMapper;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Goods;
import net.seehope.pojo.Invoices;
import net.seehope.pojo.Orders;
import net.seehope.pojo.Users;
import net.seehope.pojo.bo.PayBo;
import net.seehope.pojo.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UsersMapper usersMapper;

    @Autowired
    IndexService indexService;
    @Autowired
    InvoicesMapper invoicesMapper;
    @Override
    public String getTodayIncome() {
        long todays = indexService.getStartTime();
        long todaye = indexService.getEndTime();

        List<Orders> ordersList =  ordersMapper.getIncome(new Date(todays),new Date(todaye));
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += orders.getOrderAmout();
        }

        return sum+"";
    }

    @Override
    public String getMonthIncome() {

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));//东八区时间

        //获取本月最小天数
        int day = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        System.out.println(day);
        //获取本月最大天数
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(days);
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);

        Date dates = c.getTime();

        c.set(Calendar.DAY_OF_MONTH,days);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        c.set(Calendar.MILLISECOND,999);

        Date datee = c.getTime();

        List<Orders> ordersList =  ordersMapper.getIncome(dates,datee);
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += orders.getOrderAmout();
        }
        return sum +"";
    }

    @Override
    public String totalIncome() {
        List<Orders> ordersList = ordersMapper.selectAll();
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += orders.getOrderAmout();
        }
        return sum + "";
    }

    @Override
    public List<OrderVo> getAllIncome() {
        Date minDate =ordersMapper.orderMinDate();
        List list = new ArrayList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minDate);

        Calendar calendar2 = Calendar.getInstance();
        Date maxDate = ordersMapper.orderMaxDate();
        calendar2.setTime(maxDate);

//        System.out.println(calendar.get(Calendar.YEAR));
//
//        System.out.println(calendar2.get(Calendar.YEAR));
//
//        System.out.println(calendar.get(Calendar.MONTH));
//
//        System.out.println(calendar2.get(Calendar.MONTH));
//


        while (true){
            Date date = calendar.getTime();
            OrderVo orderVo = new OrderVo();
            orderVo.setDate(simpleDateFormat.format(date));
            orderVo.setIncome(getTodayIncome(date));
            list.add(orderVo);

            if((calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) &&  (calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH))
                    && (calendar.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))){
                break;
            }

            calendar.add(Calendar.DAY_OF_MONTH,1);

        }


        return list;
    }

    @Override
    public String getTodayIncome(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        Date todays = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date todaye = calendar.getTime();

        List<Orders> ordersList =  ordersMapper.getIncome(todays,todaye);
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += orders.getOrderAmout();
        }

        return sum+"";


    }

    @Override
    @Transactional
    public void pretreatment(PayBo payBo) {
        Map<String,String> productMap = payBo.getProductList();
        Iterator<Map.Entry<String,String>> iterator = productMap.entrySet().iterator();
        Users users = new Users();
        users.setUserId(payBo.getPhone());
        Users usersValue = usersMapper.selectOne(users);
        String userId = UUID.randomUUID().toString();


            users.setSubscribeStatus(payBo.getFlag());
            users.setUserId(userId);
            users.setVersion("0");
            users.setAddress(payBo.getAddress());
            users.setEmail(payBo.getEmail());
            users.setPhone(payBo.getPhone());
            users.setIdentity(UserType.USER.getType());
            users.setPassword(payBo.getPhone());
            usersMapper.insert(users);

       String orderId = UUID.randomUUID().toString();

        while (iterator.hasNext()){

            Map.Entry entry = iterator.next();
            String productId = entry.getKey().toString();
            String num = entry.getValue().toString();
            Orders orders = new Orders();
            orders.setOrderId(orderId);
            orders.setProductName(productId);
            orders.setProductNumber(num);
            orders.setOrderAmout(getPrice(productId,num));
            orders.setInvoiceType(payBo.getInvoiceTitle());//发票类型
            orders.setRemark(payBo.getNote());//客户的备注
            orders.setStatus(SendStatus.UNSEND.getStatus()+"");//发货未发货
            orders.setUserId(userId);
            orders.setOrderTime(new Date());
            ordersMapper.insert(orders);
        }

        if(payBo.getInvoiceFlag().equals("需要发票"))    {
            Invoices invoices = new Invoices();
            invoices.setAccout(payBo.getAccount());
            invoices.setBank(payBo.getBank());
            invoices.setInvoiceTitle(payBo.getInvoiceTitle());
            invoices.setOrderId(orderId);
            invoices.setTaxId(payBo.getTaxId());
            invoices.setInvoiceType(payBo.getInvoiceType());
            Invoices invoicesValue = invoicesMapper.selectOne(invoices);
            if(invoicesValue == null){
                invoicesMapper.insert(invoices);
            }

        }




    }

    @Override
    public Double getPrice(String goodName, String num) {
        Goods goods = new Goods();
        goods.setProductName(goodName);
        Goods goodsValue = goodsMapper.selectOne(goods);
        if(goodsValue == null){
            throw new RuntimeException("这个商品不存在");
        }
        double price = goodsValue.getProductPrice()*Integer.parseInt(num);
        return price;
    }
}
