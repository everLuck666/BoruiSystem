package net.seehope.listener;

import net.seehope.task.MyTask;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;

@Component
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();
        Map<String, Integer> ipMap = new LinkedHashMap<String, Integer>();
        context.setAttribute("ipMap", ipMap);
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        MyTask myTask = new MyTask();
         myTask.setContext(context);
        timer.schedule(myTask,calendar.getTime(),1000*60*60*24);
    }
}
