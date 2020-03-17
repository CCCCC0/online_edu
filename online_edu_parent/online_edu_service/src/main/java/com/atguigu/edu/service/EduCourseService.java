package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.vo.request.QueryCourseCondition;
import com.atguigu.edu.vo.response.EduCourseVO;
import com.atguigu.edu.vo.response.PublishInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author CL
 * @since 2020-03-12
 */
public interface EduCourseService extends IService<EduCourse> {

    List<EduCourseVO> getCourseList();

    boolean saveCourse(EduCourseVO eduCourseVO);

    void queryPageByCondition(Page<EduCourse> coursePagee, QueryCourseCondition queryCourseCondition);

    EduCourseVO selectCourseById(String id);

    boolean updateCourse(EduCourseVO eduCourseVO);

    boolean deleteCourseById(String id);

    PublishInfo selectPublishInfoById(String id);

    boolean publishCourseById(String id);
}
