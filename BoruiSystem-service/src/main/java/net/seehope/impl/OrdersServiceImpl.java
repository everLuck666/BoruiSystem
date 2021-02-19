package net.seehope.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.seehope.OrdersService;
import net.seehope.mapper.InvoicesMapper;
import net.seehope.mapper.OrdersMapper;
import net.seehope.pojo.Invoices;
import net.seehope.pojo.Orders;
import net.seehope.pojo.bo.GetOrdersBo;
import net.seehope.pojo.bo.OrdersInfoBo;
import net.seehope.pojo.bo.TotalStatisticBo;
import net.seehope.util.ExcelFormatUtil;
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

@Service
public class OrdersServiceImpl implements OrdersService {

    Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    InvoicesMapper invoicesMapper;

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
        return null;
    }

    @Override
    public TotalStatisticBo totalStatistic() {
        return null;
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
}

