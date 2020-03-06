package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.edu.vo.request.QueryTeacherCondition;
import com.atguigu.edu.vo.response.RetVal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author CL
 * @since 2020-03-05
 * 用到的技术 - rest风格  swagger(生成API文档)  mybatis_plus
 */
@RestController
@RequestMapping("/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping
    public RetVal getAllTeacher(){
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        RetVal data = RetVal.success().data("teacherList", eduTeachers);
        return data;
    }

    @DeleteMapping("{id}")
    public RetVal deteteTeacherById(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }

    @GetMapping("queryPageTeacher/{pageNum}/{pageSize}")
    public RetVal getPage(@PathVariable long pageNum,@PathVariable long pageSize){
        Page<EduTeacher> page = new Page<>(pageNum,pageSize);
        eduTeacherService.page(page,null);  //自动注入page中

        List<EduTeacher> teachers = page.getRecords();
        long total = page.getTotal();
        return RetVal.success().data("teacherList",teachers).data("total",total);
    }

    @GetMapping("queryPageTeacherByCondition/{pageNum}/{pageSize}")
    public RetVal getPageByCondition(
            @PathVariable long pageNum,
            @PathVariable long pageSize,
            QueryTeacherCondition queryTeacherCondition
    ){
        if(queryTeacherCondition != null) {
            Page<EduTeacher> eduTeacherPage = new Page<>(pageNum, pageSize);
            eduTeacherService.pageByCondition(eduTeacherPage, queryTeacherCondition);
            List<EduTeacher> teachers = eduTeacherPage.getRecords();
            long total = eduTeacherPage.getTotal();
            return RetVal.success().data("total",total).data("teachers",teachers);
        }else{
            return RetVal.error();
        }
    }

    @PostMapping("save")
    public RetVal insertTeacher(@RequestBody EduTeacher eduTeacher){

        boolean flag = eduTeacherService.save(eduTeacher);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    @GetMapping("{id}")
    public RetVal getTeacherById(String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return RetVal.success().data("teacher",teacher);
    }

    @PostMapping("update")
    public RetVal updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
}

