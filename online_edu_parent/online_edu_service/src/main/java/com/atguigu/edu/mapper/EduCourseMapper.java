package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.vo.response.PublishInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author CL
 * @since 2020-03-12
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    PublishInfo selectPublishById(String courseId);
}
