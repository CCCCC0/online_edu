package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.entity.EduSection;
import com.atguigu.edu.handler.exceptions.EduException;
import com.atguigu.edu.mapper.EduChapterMapper;
import com.atguigu.edu.mapper.EduSectionMapper;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.edu.vo.response.ChapterVo;
import com.atguigu.edu.vo.response.SectionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author CL
 * @since 2020-03-13
 */
@Service

public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduChapterMapper eduChapterMapper;

    @Autowired
    private EduSectionMapper eduSectionMapper;

    @Override
    public List<ChapterVo> getAllChapterByCourseId(String courseId) {
        List<ChapterVo> voList = new ArrayList<>();
        if(StringUtils.isNotBlank(courseId)){
            QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseId);
            List<EduChapter> eduChapterList = eduChapterMapper.selectList(queryWrapper);
            if(eduChapterList != null && eduChapterList.size() > 0){
                for (EduChapter eduChapter : eduChapterList) {
                    ChapterVo chapterVo = new ChapterVo();
                    BeanUtils.copyProperties(eduChapter,chapterVo);
                    voList.add(chapterVo);
                }
            }
            //将所有的section查询出来
            QueryWrapper<EduSection> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id",courseId);
            List<EduSection> eduSections = eduSectionMapper.selectList(wrapper);
            if(eduSections != null && eduSections.size() > 0){
                for (ChapterVo chapterVo : voList) {
                    for (EduSection eduSection : eduSections) {
                        if(chapterVo.getId().equals(eduSection.getChapterId())){
                            SectionVo sectionVo = new SectionVo();
                            BeanUtils.copyProperties(eduSection,sectionVo);
                            chapterVo.getChildren().add(sectionVo);
                        }
                    }
                }
            }
        }
        return voList;
    }

    @Override
    public boolean insertChapterToDB(EduChapter eduChapter) {
        if(eduChapter != null){
            int insert = eduChapterMapper.insert(eduChapter);
            if(insert > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateChapterToDB(EduChapter eduChapter) {
        if(eduChapter != null){
            int update = eduChapterMapper.updateById(eduChapter);
            if(update > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public ChapterVo getChapterById(String id) {
        ChapterVo chapterVo = new ChapterVo();
        if(StringUtils.isNotBlank(id)){
            EduChapter eduChapter = eduChapterMapper.selectById(id);
            BeanUtils.copyProperties(eduChapter,chapterVo);
        }
        return chapterVo;
    }


}
