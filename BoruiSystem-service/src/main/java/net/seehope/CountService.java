package net.seehope;

import net.seehope.common.RestfulJson;
import net.seehope.pojo.vo.CountVo;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

public interface CountService {


    int getTodayPeople(Date date);


    int getAllPeople();


    List<CountVo> getAllCountList();


}
