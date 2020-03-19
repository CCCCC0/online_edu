package com.atguigu.edu.frontController;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.mapper.EduCourseMapper;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.vo.response.RetVal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/edu/front")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("queryCourseByPage/{pageNum}/{pageSize}")
    public RetVal queryCourseListByPage(
            @PathVariable long pageNum,
            @PathVariable long pageSize
    ){
        Map<String,Object> map = eduCourseService.selectCourseListByPage(pageNum,pageSize);
        return RetVal.success().data(map);
    }

    @GetMapping("queryCourseAndOtherById/{courseId}")
    public RetVal queryCourseAndOtherInfoById(
            @PathVariable String courseId
    ){
        Map<String,Object> map = eduCourseService.selectCourseAndBrotherById(courseId);
        return RetVal.success().data(map);
    }
}
