package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.vo.response.ChapterVo;
import com.atguigu.edu.vo.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author CL
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @GetMapping("getAllChapter/{id}")
    public RetVal getAllChapter(@PathVariable String id){
        List<ChapterVo> chapterList = chapterService.getAllChapterByCourseId(id);
        return RetVal.success().data("chapterList",chapterList);
    }

    @PostMapping("saveChapter")
    public RetVal saveChapter(EduChapter eduChapter){
        boolean flag = chapterService.insertChapterToDB(eduChapter);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }

    @PostMapping("updateChapter")
    public RetVal updateChapter(EduChapter eduChapter){
        boolean flag = chapterService.updateChapterToDB(eduChapter);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }

    @GetMapping("selectChapter/{id}")
    public RetVal selectChapterById(@PathVariable String id){
        ChapterVo chapter = chapterService.getChapterById(id);
        return RetVal.success().data("chapter",chapter);
    }

    @DeleteMapping("deleteChapter/{id}")
    public RetVal updateChapter(@PathVariable String id){
        boolean flag = chapterService.removeById(id);
        if(flag){
            return RetVal.success();
        }
        return RetVal.error();
    }
}

