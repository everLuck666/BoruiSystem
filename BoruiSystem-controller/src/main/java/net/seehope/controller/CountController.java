package net.seehope.controller;

import net.seehope.CountService;
import net.seehope.common.RestfulJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("count")
public class CountController {
    @Autowired
    CountService countService;
    @GetMapping(value = "today",produces="application/json;charset=UTF-8")
    public RestfulJson getTodayPeople(){
        Date date = new Date();
     return RestfulJson.isOk(countService.getTodayPeople(date));
    }

    @GetMapping(value = "all",produces="application/json;charset=UTF-8")
    public RestfulJson getAllPeople(){
        return RestfulJson.isOk(countService.getAllPeople());
    }
    @GetMapping(value = "count",produces="application/json;charset=UTF-8")
    public RestfulJson count(){
        return RestfulJson.isOk("计数中");
    }
    @GetMapping(value = "allCount",produces="application/json;charset=UTF-8")
    public RestfulJson getAllCount(){
        return RestfulJson.isOk(countService.getAllCountList());

    }
}
