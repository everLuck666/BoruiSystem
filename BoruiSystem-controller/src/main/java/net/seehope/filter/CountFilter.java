package net.seehope.filter;

import net.seehope.mapper.CountPeopleMapper;
import net.seehope.pojo.CountPeople;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class CountFilter implements Filter {
    Logger logger = LoggerFactory.getLogger("CountFilter");


    private FilterConfig filterConfig;

    public CountPeopleMapper getCountPeopleMapper() {
        return countPeopleMapper;
    }

    public void setCountPeopleMapper(CountPeopleMapper countPeopleMapper) {
        this.countPeopleMapper = countPeopleMapper;
    }

    private CountPeopleMapper countPeopleMapper;



    @Override
    public void destroy() {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletContext context = filterConfig.getServletContext();
        Map<String, Integer> ipMap = (Map<String, Integer>) context.getAttribute("ipMap");
        // 获取请求ip地址
        HttpServletRequest req = (HttpServletRequest)request;
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        if (ip.indexOf(",") != -1) {
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
        System.out.println("---------------------");
        System.out.println("ip:"+ip);
        System.out.println("-------------------------");
        if(ipMap.containsKey(ip)){
            Integer count = ipMap.get(ip);
            logger.info("今天这个用户已经登录过了");


            ipMap.put(ip,count+1);
        }else{



            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(date);


            if(!ipMap.containsKey(ip)){
                synchronized (this){
                    if(!ipMap.containsKey(ip)){
                        ipMap.put(ip,1);

                            CountPeople countPeopleTemp = new CountPeople();
                            countPeopleTemp.setTime(time);
                            CountPeople countPeopleValue = countPeopleMapper.selectOne(countPeopleTemp);
                            if(countPeopleValue == null){
                                countPeopleTemp.setCount("1");
                                countPeopleMapper.insert(countPeopleTemp);
                                logger.info("今天第一次记录访客");
                            }else {
                                Integer countPeople = Integer.parseInt(countPeopleValue.getCount());
                                countPeople++;
                                countPeopleMapper.delete(countPeopleValue);
                                countPeopleValue.setCount(countPeople + "");
                                countPeopleMapper.insert(countPeopleValue);
                            }

                    }

                }
            }




        }
        chain.doFilter(request, response);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
}

