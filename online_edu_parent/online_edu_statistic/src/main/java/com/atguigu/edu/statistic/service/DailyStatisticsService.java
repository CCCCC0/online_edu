package com.atguigu.edu.statistic.service;

import com.atguigu.edu.statistic.entity.DailyStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author CL
 * @since 2020-03-17
 */
public interface DailyStatisticsService extends IService<DailyStatistics> {

    boolean saveStatistic(String day);

    Map<String, Object> getStatistics(String dataType, String beginTime, String endTime);
}
