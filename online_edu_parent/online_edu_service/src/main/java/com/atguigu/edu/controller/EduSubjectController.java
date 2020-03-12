package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.service.EduSubjectService;
import com.atguigu.edu.vo.response.EduSubjectVO;
import com.atguigu.edu.vo.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author CL
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/edu")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @GetMapping("all/subject")
    public RetVal getAllSubject(){
        List<EduSubjectVO> eduSubjectVOS = eduSubjectService.selectAllSub();
        if(eduSubjectVOS != null || eduSubjectVOS.size() > 0){
            return RetVal.success().data("allSubject",eduSubjectVOS);
        }else{
            return RetVal.error();
        }
    }

    @PostMapping("upload/xlsFile")
    public RetVal uploadXlsFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<String> stringList = eduSubjectService.insertXlsToDB(multipartFile);
        if(stringList != null && stringList.size() > 0){
            return RetVal.error().data("errorInfo",stringList);
        }
        return RetVal.success();
    }

    @DeleteMapping("delete/{id}")
    public RetVal deleteEduSubject(@PathVariable String id){
        //删除时 - 需要判断该节点下是否有子节点  也要判断当前节点是否存在
        boolean flag = eduSubjectService.deleteSubjectById(id);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }

    @PostMapping("save/levelOne")
    public RetVal saveLevelOneSubject(EduSubject eduSubject){
        //进行一级学科的添加
        boolean flag = eduSubjectService.saveLevelOneSub(eduSubject);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }

    @PostMapping("save/levelTwo")
    public RetVal saveLevelTwoSubject(EduSubject eduSubject){
        //进行二级学科的添加
        boolean flag = eduSubjectService.saveLevelTwoSub(eduSubject);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }
}

