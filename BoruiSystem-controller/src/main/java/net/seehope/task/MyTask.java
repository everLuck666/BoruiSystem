package net.seehope.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimerTask;

public class MyTask extends TimerTask {
    private ServletContext context;

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }
    @Override
    public void run() {
        Map<String, Integer> ipMap = new LinkedHashMap<String, Integer>();
        context.setAttribute("ipMap", ipMap);
        System.out.println("我执行了");
    }
}
