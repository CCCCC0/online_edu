package com.atguigu.edu.controller;

import com.atguigu.edu.entity.EduSection;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.edu.vo.response.RetVal;
import com.atguigu.edu.vo.response.SectionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程小节 前端控制器
 * </p>
 *
 * @author CL
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/edu/section")
@CrossOrigin
public class EduSectionController {

    @Autowired
    private EduSectionService eduSectionService;

    @PostMapping("saveSection")
    public RetVal saveEduSection(EduSection eduSection){
        boolean flag = eduSectionService.addEduSection(eduSection);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }

    @DeleteMapping("deleteSection/{id}")
    public RetVal deleteSectionById(@PathVariable String id){
        boolean flag = eduSectionService.removeById(id);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }

    @GetMapping("selectSection/{id}")
    public RetVal selectSectionById(@PathVariable String id){
        SectionVo sectionVo = eduSectionService.getSectionVOById(id);
        return RetVal.success().data("section",sectionVo);
    }

    @PostMapping("updateSection")
    public RetVal updateEduSection(SectionVo sectionVo){
        boolean flag = eduSectionService.updateEduSection(sectionVo);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }

}

