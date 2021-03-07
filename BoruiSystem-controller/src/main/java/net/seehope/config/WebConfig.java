package net.seehope.config;

import net.seehope.UserService;
import net.seehope.common.UserType;
import net.seehope.filter.AllowOriginFilter;
import net.seehope.filter.CountFilter;
import net.seehope.interceptor.MyInterceptor;
import net.seehope.mapper.CountPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    UserService userService;

    @Autowired
    CountPeopleMapper countPeopleMapper;

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        AllowOriginFilter myFilter = new AllowOriginFilter();


        filterBean.setFilter(myFilter);

        CountFilter countFilter = new CountFilter();
        countFilter.setCountPeopleMapper(countPeopleMapper);
        filterBean.setFilter(countFilter);
        ArrayList urls = new ArrayList();
        urls.add("/*");
        filterBean.setUrlPatterns(urls);
        Map map = new HashMap();
        map.put("count", "100");
        filterBean.setInitParameters(map);


        return filterBean;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        // registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        File file = new File("BoruiSystem-controller");
        registry.addResourceHandler("/static/**").addResourceLocations("file:"+file.getAbsolutePath()+"/src/main/resources/static/");



    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //后台
        List includePathLists = new ArrayList();
        List excludePathList = new ArrayList();
        excludePathList.add("/count/count");
        excludePathList.add("/user/manager");
//        excludePathList.add("/wxPay/success");
//        excludePathList.add("/wxPay/alinotify");
        excludePathList.add("/wxPay/**");
        
        excludePathList.add("/file/download/*");
        excludePathList.add("/goods/goods");
        excludePathList.add("/file/download/**");
        includePathLists.add("/count/**");
        includePathLists.add("/goods/**");
        includePathLists.add("/orders/**");
        includePathLists.add("/sms/**");
        includePathLists.add("/file/**");
        includePathLists.add("/user/**");
        includePathLists.add("/users/**");

        registry.addInterceptor(new MyInterceptor(new String[]{UserType.SUPERMANAGER.getType() + ""}, "不是管理员token", userService))
                .addPathPatterns(includePathLists)
        .excludePathPatterns(excludePathList);
//
//
//        //拦截学生
//        List studentIncludePathLists = new ArrayList();
//        studentIncludePathLists.add("/choose/choose");
//        registry.addInterceptor(new MyInterceptor(new String[]{UserType.STUDENT.getType() + ""}, "不是学生token", userService))
//                .addPathPatterns(studentIncludePathLists);

//管理员拦截器
//        List managerIncludePathLists = new ArrayList();
//        managerIncludePathLists.add("/staff/**");
//        managerIncludePathLists.add("/orderStatistic/**");
//        managerIncludePathLists.add("/webVerifi/**");
//        managerIncludePathLists.add("/ticketsManager/**");
//        List managerExclude = new ArrayList();
//        managerExclude.add("/staff/getStaffs");
//        registry.addInterceptor(new MyInterceptor(new String[]{UserType.MANAGER.getType()+"",UserType.SUPERMANAGER.getType()+""},"不是管理员token或者超级管理员",userService))
//                .addPathPatterns(managerIncludePathLists);
////
////超级管理员拦截器
//        List superManagerIncludePathLists = new ArrayList();
//        superManagerIncludePathLists.add("/admin/Admin/**");
//        superManagerIncludePathLists.add("/admin/AdminInfo");
//        superManagerIncludePathLists.add("/manager/incomeStatistics/**");
//
//        registry.addInterceptor(new MyInterceptor(new String[]{UserType.SUPERMANAGER.getType()+""},"不是超级管理token",userService))
//                .addPathPatterns(superManagerIncludePathLists);
//
//        //员工拦截器
//        List workIncludePathLists = new ArrayList();
//        workIncludePathLists.add("/admin/getOrderMsg");
//        workIncludePathLists.add("/admin/verification");
//        workIncludePathLists.add("/admin/getOrderMsg");
//        workIncludePathLists.add("/admin/verification");
//        workIncludePathLists.add("/admin/getRecord");
//        registry.addInterceptor(new MyInterceptor(new String[]{UserType.WORK.getType()+""},"不是员工token或者没有权限",userService))
//                .addPathPatterns(workIncludePathLists);


    }
}
