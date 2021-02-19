package net.seehope.filter;

import net.seehope.mapper.CountPeopleMapper;
import net.seehope.pojo.CountPeople;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
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
        String ip = request.getRemoteAddr();
        System.out.println("---------------------");
        System.out.println("ip:"+ip);
        System.out.println("-------------------------");
        if(ipMap.containsKey(ip)){
            Integer count = ipMap.get(ip);
            logger.info("今天这个用户已经登录过了");


            ipMap.put(ip,count+1);
        }else{
            ipMap.put(ip,1);

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(date);

            synchronized (this){
                CountPeople countPeopleTemp = new CountPeople();
                countPeopleTemp.setTime(time);
                CountPeople countPeopleValue = countPeopleMapper.selectOne(countPeopleTemp);
                if(countPeopleValue == null){
                    countPeopleTemp.setCount("1");
                    countPeopleMapper.insert(countPeopleTemp);
                    logger.info("今天第一次记录访客");
                }else{
                    Integer countPeople = Integer.parseInt(countPeopleValue.getCount());
                    countPeople++;
                    countPeopleMapper.delete(countPeopleValue);
                    countPeopleValue.setCount(countPeople+"");
                    countPeopleMapper.insert(countPeopleValue);
                }
            }

        }
        context.setAttribute("ipMap", ipMap);
        chain.doFilter(request, response);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
}

