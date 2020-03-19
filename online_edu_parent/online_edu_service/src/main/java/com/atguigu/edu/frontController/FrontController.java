package com.atguigu.edu.frontController;

import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.edu.vo.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/edu/front")
public class FrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("getPageTeacher/{pageNum}/{pageSize}")
    public RetVal getPageTeacher(
            @PathVariable Long pageNum,
            @PathVariable Long pageSize
    ){
        Map<String,Object> map = eduTeacherService.selectTeacherByPage(pageNum,pageSize);
        return RetVal.success().data(map);
    }

    @GetMapping("queryTeacherById/{teacherId}")
    public RetVal queryTeacherById(@PathVariable String teacherId){
        Map<String,Object> map = eduTeacherService.queryTeacherAndCourseById(teacherId);
        return RetVal.success().data(map);
    }
}
