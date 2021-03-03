package net.seehope.impl;

import net.seehope.CountService;
import net.seehope.mapper.CountPeopleMapper;
import net.seehope.pojo.CountPeople;
import net.seehope.pojo.vo.CountVo;
import net.seehope.pojo.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CountServiceImpl implements CountService {
    @Autowired
    CountPeopleMapper countPeopleMapper;
    @Override
    public int getTodayPeople(Date date) {
        CountPeople countPeople = new CountPeople();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        countPeople.setTime(simpleDateFormat.format(date));

        CountPeople countPeopleValue = countPeopleMapper.selectOne(countPeople);
        if(countPeopleValue != null){
            String num = countPeopleValue.getCount();
            if(num != null){
                return Integer.parseInt(num);
            }else {
                return 0;
            }
        }else {
            return 0;
        }


    }

    @Override
    public int getAllPeople() {
        List<CountPeople> countPeopleList = countPeopleMapper.selectAll();
        int sum = 0;
        for(CountPeople countPeople:countPeopleList){
            sum += Integer.parseInt(countPeople.getCount());
        }
        return sum;
    }

    @Override
    public List<CountVo> getAllCountList() {

        List<CountVo> list = new ArrayList<>();

        Date start = countPeopleMapper.countMinDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        Date end = countPeopleMapper.countMaxDate();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(end);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (true){
            Date date = calendar.getTime();
            CountVo countVo = new CountVo();
            countVo.setDate(simpleDateFormat.format(date));
            countVo.setCount(getTodayPeople(date)+"");
            list.add(countVo);

            if((calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) &&  (calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH))
                    && (calendar.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))){
                break;
            }

            calendar.add(Calendar.DAY_OF_MONTH,1);

        }


        return list;
    }


    }

