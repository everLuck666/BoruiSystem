package net.seehope.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.seehope.IndexService;
import net.seehope.OrdersService;
import net.seehope.common.GoodsStatus;
import net.seehope.common.OrderType;
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
import net.seehope.pojo.bo.*;
import net.seehope.pojo.vo.OrderVo;
import net.seehope.util.ExcelFormatUtil;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {

    Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    InvoicesMapper invoicesMapper;

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UsersMapper usersMapper;

    @Autowired
    IndexService indexService;

    @Override
    public Integer getWaitingOrders() {
        return ordersMapper.queryWaitingOrders();
    }

    @Override
    public Integer getFinishedOrders() {
        return ordersMapper.queryFinishedOrders();
    }

    @Override
    public List salesStatistic() {

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

        long todays = indexService.getStartTime();
        long todaye = indexService.getEndTime();

        List<SalesStatisticBo> saleBo = null;

        List<GoodsInfoBo> goods = ordersMapper.getProductInfo();
        for (GoodsInfoBo good:goods){
            Integer today = ordersMapper.querySales(good.getProductName(),String.valueOf(todays),String.valueOf(todaye));
            Integer month = ordersMapper.querySales(good.getProductName(),String.valueOf(days),String.valueOf(datee));
            Integer total = ordersMapper.queryTotalSales(good.getProductName());
            SalesStatisticBo salesStatisticBo = new SalesStatisticBo();
            salesStatisticBo.setProductName(good.getProductName());
            salesStatisticBo.setProductPrice(good.getProductPrice());
            salesStatisticBo.setToday(today.toString());
            salesStatisticBo.setMonth(month.toString());
            salesStatisticBo.setTotal(total.toString());
            System.out.println("------------"+salesStatisticBo);
            saleBo.add(salesStatisticBo);
        }
        return saleBo;
    }

    @Override
    public TotalStatisticBo totalStatistic() {

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

        long todays = indexService.getStartTime();
        long todaye = indexService.getEndTime();

        TotalStatisticBo totalStatisticBo = new TotalStatisticBo();
        Integer today = ordersMapper.queryTotalStatistics(new Date(todays),new Date(todaye));
        Integer month = ordersMapper.queryTotalStatistics(dates,datee);
        Integer total = ordersMapper.queryTotal();
        totalStatisticBo.setDay(today.toString());
        totalStatisticBo.setMonth(month.toString());
        totalStatisticBo.setTotal(total.toString());
        System.out.println("***********"+totalStatisticBo);
        return totalStatisticBo;
    }

    @Override
    public PageInfo getAllOrders(GetOrdersBo ordersBo) {
        PageHelper.startPage(ordersBo.getPage(),ordersBo.getPageSize());
        List orders = ordersMapper.queryOrdersBy(ordersBo.getStatus(),ordersBo.getOrderId());
        PageInfo pageInfo = new PageInfo(orders);
        return pageInfo;
    }

    @Override
    public void updateOrder(String orderId) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        Orders orders1 = ordersMapper.selectOne(orders);
        ordersMapper.delete(orders);
        orders1.setStatus("1");
        ordersMapper.insert(orders1);
    }

    /**
     * 获取订单发票信息
     * @param orderId
     * @return
     */
    @Override
    public Invoices getInvoices(String orderId) {
        Invoices invoices = new Invoices();
        invoices.setOrderId(orderId);
        return invoicesMapper.selectOne(invoices);
    }

    /**
     * 导出订单数据到Excel表
     * @param request
     * @param response
     * @param excelName
     * @return
     */
    @Override
    public ResponseEntity<byte[]> exportOrderExcel(HttpServletRequest request, HttpServletResponse response, String excelName) {
        try {
            logger.info(">>>>>>>>>>开始导出excel>>>>>>>>>>");


            List<OrdersInfoBo> list=ordersMapper.queryAllOrders();

            BaseFrontServiceImpl baseFrontController = new BaseFrontServiceImpl();
            return baseFrontController.buildResponseEntity(export(list), excelName + ".xlsx");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>导出excel 异常，原因为：" + e.getMessage());
        }
        return null;
    }

    @Override
    public String getTodaySales() {
        return null;
    }

    @Override
    public String getMonthSales() {
        return null;
    }

    private InputStream export(List<OrdersInfoBo> list) {
        logger.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
        Map<String, String> map = new HashMap();
        map.put("0","未发货");
        map.put("1","已发货");
        map.put("2","无");
        map.put("3","普通发票");
        map.put("4","专用发票");
        ByteArrayOutputStream output = null;
        InputStream inputStream1 = null;
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = ExcelFormatUtil.headSytle(wb);// cell样式
        CellStyle content = ExcelFormatUtil.contentStyle(wb);// 报表体样式

        // 每一列字段名
        String[] strs = new String[] { "订单号", "商品名称", "商品数量", "买家姓名", "联系方式",
                "收货地址","订单总额","下单时间","备注信息","发票","状态"};

        // 字段名所在表格的宽度
        int[] ints = new int[] { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};

        // 设置表头样式
        ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
        logger.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");

        if (list != null && list.size() > 0) {
            logger.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
            for (int i = 0; i < list.size(); i++) {
                OrdersInfoBo user = list.get(i);
                SXSSFRow row = sheet.createRow(i + 1);
                int j = 0;

                SXSSFCell cell = row.createCell(j++);
                cell.setCellValue(user.getOrderId());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getProductName());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getProductNumber());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getUserName());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getPhone());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getAddress());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getOrderAmout());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getOrderTime());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(user.getRemark());
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(map.get(user.getInvoicesType()));
                cell.setCellStyle(content);

                cell = row.createCell(j++);
                cell.setCellValue(map.get(map.get(user.getStatus())));
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
    public String getTodayIncome() {
        long todays = indexService.getStartTime();
        long todaye = indexService.getEndTime();

        List<Orders> ordersList =  ordersMapper.getIncome(new Date(todays),new Date(todaye));
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += Integer.parseInt(orders.getOrderAmout());
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

            sum += Integer.parseInt(orders.getOrderAmout());
        }
        return sum +"";
    }

    @Override
    public String totalIncome() {
        List<Orders> ordersList = ordersMapper.selectAll();
        int sum = 0;
        for(Orders orders:ordersList){
            System.out.println(orders.getOrderAmout());

            sum += Integer.parseInt(orders.getOrderAmout());
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

            sum += Integer.parseInt(orders.getOrderAmout());
        }

        return sum+"";


    }

    @Override
    @Transactional
    public String[] pretreatment(PayBo payBo) {
        Map<String,String> productMap = payBo.getProductList();
        Iterator<Map.Entry<String,String>> iterator = productMap.entrySet().iterator();
        Users users = new Users();
        users.setUserId(payBo.getPhone());
        Users usersValue = usersMapper.selectOne(users);
        String userId = UUID.randomUUID().toString();


            users.setSubscribeStatus(payBo.getFlag());
            users.setUserId(userId);
            users.setUserName(payBo.getUserName());
            users.setVersion("0");
            users.setAddress(payBo.getAddress());
            users.setEmail(payBo.getEmail());
            users.setPhone(payBo.getPhone());
            users.setIdentity(UserType.USER.getType());
            users.setPassword(payBo.getPhone());
            usersMapper.insert(users);

       String orderId = UUID.randomUUID().toString().replace("-","");
       int totalPrice = 0;

        while (iterator.hasNext()){

            Map.Entry entry = iterator.next();
            String productId = entry.getKey().toString();
            String num = entry.getValue().toString();
            Orders orders = new Orders();
            orders.setOrderId(orderId);
            orders.setProductName(productId);
            orders.setProductNumber(num);
            int priceTemp = getPrice(productId,num);
            totalPrice += priceTemp;

            orders.setOrderAmout(priceTemp+"");
            orders.setInvoiceType(payBo.getInvoiceTitle());//发票类型
            orders.setRemark(payBo.getNote());//客户的备注
            orders.setStatus(SendStatus.UNSEND.getStatus()+"");//发货未发货
            orders.setUserId(userId);
            orders.setOrderStatus(OrderType.NOFINISH.getType()+"");
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
        String[] message = new String[]{orderId,totalPrice+""};

        return message;




    }

    @Override
    public int getPrice(String goodName, String num) {
        Goods goods = new Goods();
        goods.setProductName(goodName);
        Goods goodsValue = goodsMapper.selectOne(goods);
        if(goodsValue == null){
            throw new RuntimeException("这个商品不存在");
        }
        if(StringUtils.equals(goodsValue.getStatus(), GoodsStatus.OFF.getStatus()+"")){
            throw new RuntimeException("这个商品已经下架");
        }
        int price = Integer.parseInt(goodsValue.getProductPrice())*Integer.parseInt(num);
        return price;
    }

    @Override
    @Transactional
    public void finishOrder(String orderId) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);

        List<Orders> ordersList = ordersMapper.select(orders);
        for(Orders order:ordersList){
            ordersMapper.delete(order);
            order.setOrderStatus(OrderType.FINISH.getType()+"");
            ordersMapper.insert(order);

        }
    }

    @Override
    public boolean isOrderFinish(String orderId) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        List<Orders> ordersList = ordersMapper.select(orders);
        for(Orders order:ordersList){
            if(StringUtils.equals(order.getOrderStatus(),OrderType.FINISH.getType()+"")){
                return true;
            }
        }
        return false;
    }
}
