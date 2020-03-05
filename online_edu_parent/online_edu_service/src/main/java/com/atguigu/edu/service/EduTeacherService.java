package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.vo.request.QueryTeacherCondition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author CL
 * @since 2020-03-05
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageByCondition(Page<EduTeacher> eduTeacherPage, QueryTeacherCondition queryTeacherCondition);
}
