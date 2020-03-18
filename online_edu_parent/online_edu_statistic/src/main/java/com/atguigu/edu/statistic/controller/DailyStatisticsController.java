package com.atguigu.edu.statistic.controller;

import com.atguigu.edu.statistic.service.DailyStatisticsService;
import com.atguigu.edu.vo.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author CL
 * @since 2020-03-17
 */
@RestController
@RequestMapping("/daily/statistics")
@CrossOrigin
public class DailyStatisticsController {

    @Autowired
    private DailyStatisticsService dailyStatisticsService;

    @PostMapping("save/{day}")
    public RetVal saveStatisticDaily(@PathVariable String day){
        boolean flag = dailyStatisticsService.saveStatistic(day);
        if(flag){
        return RetVal.success(); }
        return RetVal.error();
    }

    @GetMapping("selectDailyStatistic/{dataType}/{startTime}/{endTime}")
    public RetVal selectDailyStatisticByday(
            @PathVariable String dataType,
            @PathVariable String startTime,
            @PathVariable String endTime
    ){
        Map<String,Object> map =
                dailyStatisticsService.getStatistics(dataType,startTime,endTime);
        return RetVal.success().data("dayInfo",map);
    }

}

