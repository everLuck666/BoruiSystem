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
    @GetMapping("today")
    public RestfulJson getTodayPeople(){
        Date date = new Date();
     return RestfulJson.isOk(countService.getTodayPeople(date));
    }

    @GetMapping("all")
    public RestfulJson getAllPeople(){
        return RestfulJson.isOk(countService.getAllPeople());
    }
    @GetMapping("count")
    public RestfulJson count(){
        return RestfulJson.isOk("计数中");
    }
    @GetMapping("allCount")
    public RestfulJson getAllCount(){
        return RestfulJson.isOk(countService.getAllCountList());

    }
}
