package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.vo.request.QueryCourseCondition;
import com.atguigu.edu.vo.response.EduCourseVO;
import com.atguigu.edu.vo.response.RetVal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author CL
 * @since 2020-03-12
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("getAllCourse")
    public RetVal getAllCourse(){
        List<EduCourseVO> courseList = eduCourseService.getCourseList();
        return RetVal.success().data("courseList",courseList);
    }

    @PostMapping("save")
    public RetVal saveCourse(EduCourseVO eduCourseVO){
        boolean flag = eduCourseService.saveCourse(eduCourseVO);
        if (flag){
            return RetVal.success();
        }
        return RetVal.error();
    }

    @PostMapping("queryPageByCondition/{pageNum}/{pageSize}")
    public RetVal queryCoursePageByCondition(
            @PathVariable long pageNum,
            @PathVariable long pageSize,
            QueryCourseCondition queryCourseCondition){
        Page<EduCourse> coursePage = new Page<>(pageNum,pageSize);
        eduCourseService.queryPageByCondition(coursePage,queryCourseCondition);
        List<EduCourse> courseList = coursePage.getRecords();
        long pageTotal = coursePage.getTotal();
        return RetVal.success().data("pageTotal",pageTotal).data("courseList",courseList);
    }

    @GetMapping("getCourseById/{id}")
    public RetVal selectCourseById(@PathVariable String id){
        EduCourseVO eduCourseVO = eduCourseService.selectCourseById(id);
        return RetVal.success().data("courseInfo",eduCourseVO);
    }

    @PostMapping("update")
    public RetVal updateCourse(EduCourseVO eduCourseVO) {
        boolean flag = eduCourseService.updateCourse(eduCourseVO);
        if (flag) {
            return RetVal.success();
        }
        return RetVal.error();
    }

    @DeleteMapping("delete/{id}")
    public RetVal deleteCourseById(@PathVariable String id){
        boolean flag = eduCourseService.deleteCourseById(id);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }
}

