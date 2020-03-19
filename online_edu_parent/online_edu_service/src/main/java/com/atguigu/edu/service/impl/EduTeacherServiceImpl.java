package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduCourseDescription;
import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.mapper.EduCourseDescriptionMapper;
import com.atguigu.edu.mapper.EduCourseMapper;
import com.atguigu.edu.mapper.EduTeacherMapper;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.edu.vo.request.QueryTeacherCondition;
import com.atguigu.edu.vo.response.EduCourseVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author CL
 * @since 2020-03-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeacherMapper eduTeacherMapper;

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Override
    public void pageByCondition(Page<EduTeacher> eduTeacherPage, QueryTeacherCondition queryTeacherCondition) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = queryTeacherCondition.getName();
        Integer level = queryTeacherCondition.getLevel();
        String gmtCreate = queryTeacherCondition.getGmtCreate();
        String gmtModified = queryTeacherCondition.getGmtModified();

        if(StringUtils.isNotBlank(name)){
            wrapper.like("name",name);
        }
        if(level != null){
            wrapper.eq("level",level);
        }
        if(StringUtils.isNotBlank(gmtCreate)){
            wrapper.gt("gmt_create",gmtCreate);
        }
        if(StringUtils.isNotBlank(gmtModified)){
            wrapper.lt("gmt_modified",gmtModified);
        }
        eduTeacherMapper.selectPage(eduTeacherPage,wrapper);
    }

    @Override
    public Map<String, Object> selectTeacherByPage(long pageNum, long pageSize) {
        Map<String,Object> map = new HashMap<>();
        Page<EduTeacher> eduTeacherPage = new Page<>(pageNum, pageSize);
        eduTeacherMapper.selectPage(eduTeacherPage,null);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long current = eduTeacherPage.getCurrent();
        long size = eduTeacherPage.getSize();
        boolean hasNext = eduTeacherPage.hasNext();
        boolean hasPrevious = eduTeacherPage.hasPrevious();
        map.put("hasPrevious",hasPrevious);
        map.put("currentPage",current);
        map.put("hasNext",hasNext);
        map.put("pages",size);
        map.put("teacherList",records);

        return map;
    }

    @Override
    public Map<String, Object> queryTeacherAndCourseById(String teacherId) {
        Map<String, Object> map = new HashMap<>();
        EduTeacher eduTeacher = eduTeacherMapper.selectById(teacherId);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> eduCourses = eduCourseMapper.selectList(queryWrapper);
        List<EduCourseVO> courseVOS = new ArrayList<>();
        if(eduCourses != null && eduCourses.size() > 0){
            for (EduCourse eduCours : eduCourses) {
                String coursId = eduCours.getId();
                EduCourseDescription eduCourseDescription = eduCourseDescriptionMapper.selectById(coursId);
                EduCourseVO courseVO = new EduCourseVO();
                BeanUtils.copyProperties(eduCours,courseVO);
                if(eduCourseDescription != null) {
                    courseVO.setDescription(eduCourseDescription.getDescription());
                }
                courseVOS.add(courseVO);
            }
        }
        map.put("teacher",eduTeacher);
        map.put("courseList",courseVOS);
        return map;
    }

}
