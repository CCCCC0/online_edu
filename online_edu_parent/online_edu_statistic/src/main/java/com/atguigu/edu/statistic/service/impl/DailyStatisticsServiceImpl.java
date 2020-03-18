package com.atguigu.edu.statistic.service.impl;

import com.atguigu.edu.statistic.entity.DailyStatistics;
import com.atguigu.edu.statistic.mapper.DailyStatisticsMapper;
import com.atguigu.edu.statistic.service.DailyStatisticsService;
import com.atguigu.edu.statistic.service.UserService;
import com.atguigu.edu.vo.response.RetVal;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author CL
 * @since 2020-03-17
 */
@Service
public class DailyStatisticsServiceImpl extends ServiceImpl<DailyStatisticsMapper, DailyStatistics> implements DailyStatisticsService {

    @Autowired
    private UserService userService;

    @Autowired
    private DailyStatisticsMapper dailyStatisticsMapper;

    @Override
    public boolean saveStatistic(String day) {
        if(StringUtils.isNotBlank(day)){
            //调用user服务获取注册人数
            RetVal retVal = userService.getDayRegisterUserData(day);
            Map<String, Object> data = retVal.getData();
            Integer count = (Integer)data.get("count");
            //编写其他数据
            Integer loginNum = RandomUtils.nextInt() + 10;
            Integer videoViewNum = RandomUtils.nextInt() + 5;
            Integer consumerNum = RandomUtils.nextInt() + 13;
            DailyStatistics dailyStatistics = new DailyStatistics();
            dailyStatistics.setLoginNum(loginNum);
            dailyStatistics.setVideoViewNum(videoViewNum);
            dailyStatistics.setRegisterNum(count);
            dailyStatistics.setCourseNum(consumerNum);
            dailyStatistics.setDateCalculated(day);
            int insert = dailyStatisticsMapper.insert(dailyStatistics);
            if(insert > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getStatistics(String dataType, String beginTime, String endTime) {
        //返回两个list集合信息
        QueryWrapper<DailyStatistics> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", beginTime, endTime);
        wrapper.orderByAsc("date_calculated");
        List<DailyStatistics> dailyStatistics = baseMapper.selectList(wrapper);

        //一个是统计日期(x)
        ArrayList<String> xData = new ArrayList<>();
        //一个根据选择的数据类型返回对应数量
        ArrayList<Integer> yData = new ArrayList<>();
        for (DailyStatistics dailyStatistic : dailyStatistics) {
            String dateCalculated = dailyStatistic.getDateCalculated();
            xData.add(dateCalculated);
            switch (dataType) {
                case "register_num":
                    Integer registerNum = dailyStatistic.getRegisterNum();
                    yData.add(registerNum);
                    break;
                case "login_num":
                    Integer loginNum = dailyStatistic.getLoginNum();
                    yData.add(loginNum);
                    break;
                case "video_view_num":
                    Integer videoViewNum = dailyStatistic.getVideoViewNum();
                    yData.add(videoViewNum);
                    break;
                case "course_num":
                    Integer courseNum = dailyStatistic.getCourseNum();
                    yData.add(courseNum);
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> xyData = new HashMap<>();
        xyData.put("xData",xData);
        xyData.put("yData",yData);
        return xyData;
    }


}
