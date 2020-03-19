package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduCourseDescription;
import com.atguigu.edu.mapper.EduChapterMapper;
import com.atguigu.edu.mapper.EduCourseDescriptionMapper;
import com.atguigu.edu.mapper.EduCourseMapper;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.vo.request.QueryCourseCondition;
import com.atguigu.edu.vo.response.ChapterVo;
import com.atguigu.edu.vo.response.CourseAndBrother;
import com.atguigu.edu.vo.response.EduCourseVO;
import com.atguigu.edu.vo.response.PublishInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author CL
 * @since 2020-03-12
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Autowired
    private EduChapterMapper eduChapterMapper;

    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public List<EduCourseVO> getCourseList() {
        //查询出所有的课程
        List<EduCourse> eduCourses = eduCourseMapper.selectList(null);
        //查询出所有的课程描述信息
        List<EduCourseDescription> descriptions = eduCourseDescriptionMapper.selectList(null);
        List<EduCourseVO> voList = new ArrayList<>();
        //复制课程信息
        for (EduCourse eduCours : eduCourses) {
            EduCourseVO subjectVO = new EduCourseVO();
            BeanUtils.copyProperties(eduCours,subjectVO);
        }

        if(descriptions != null && descriptions.size() > 0){
            for (EduCourseDescription description : descriptions) {
                for (EduCourseVO courseVO : voList) {
                    if(description.getId().equals(courseVO.getId())){
                        courseVO.setDescription(description.getDescription());
                    }
                }
            }
        }

        return voList;
    }

    //进行课程的保存
    @Override
    public boolean saveCourse(EduCourseVO eduCourseVO) {
        if(eduCourseVO != null){
            EduCourse eduCourse = new EduCourse();
            EduCourseDescription description = new EduCourseDescription();
            BeanUtils.copyProperties(eduCourseVO,eduCourse);
            int rows = eduCourseMapper.insert(eduCourse);
            if(rows > 0){
                String courseId = eduCourse.getId();
                description.setId(courseId);
                description.setDescription(eduCourseVO.getDescription());
                int flag = eduCourseDescriptionMapper.insert(description);
                if(flag > 0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void queryPageByCondition(Page<EduCourse> coursePagee, QueryCourseCondition queryCourseCondition) {
        List<EduCourseVO> ovList = new ArrayList<>();
        if(queryCourseCondition != null){
            String status = queryCourseCondition.getStatus();
            String title = queryCourseCondition.getTitle();
            //添加条件
            QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(status)){
                queryWrapper.eq("status",status);
            }
            if(StringUtils.isNotBlank(title)){
                queryWrapper.eq("title",title);
            }
            eduCourseMapper.selectPage(coursePagee, queryWrapper);
        }
    }

    @Override
    public EduCourseVO selectCourseById(String id) {
        EduCourseVO eduCourseVO = new EduCourseVO();
        if(StringUtils.isNotBlank(id)){
            EduCourse eduCourse = eduCourseMapper.selectById(id);
            EduCourseDescription eduCourseDescription = eduCourseDescriptionMapper.selectById(id);
            BeanUtils.copyProperties(eduCourse,eduCourseVO);
            eduCourseVO.setDescription(eduCourseDescription.getDescription());
        }
        return eduCourseVO;
    }

    @Override
    public boolean updateCourse(EduCourseVO eduCourseVO) {
        if(eduCourseVO != null){
            //进行课程的更新
            EduCourse course = new EduCourse();
            BeanUtils.copyProperties(eduCourseVO,course);
            eduCourseMapper.updateById(course);
            //进行课程描述的更新
            String description = eduCourseVO.getDescription();
            String id = course.getId();
            EduCourseDescription courseDescription = new EduCourseDescription();
            courseDescription.setId(id);
            courseDescription.setDescription(description);
            eduCourseDescriptionMapper.updateById(courseDescription);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCourseById(String id) {
        if(StringUtils.isNotBlank(id)){
             //进行课程删除时 - 需删除课程下 所有章节 章节下所有小节  小节中所有的视频
            eduCourseMapper.deleteById(id);
            eduCourseDescriptionMapper.deleteById(id);
            QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",id);
            List<EduChapter> chapterList = eduChapterMapper.selectList(queryWrapper);
            if(chapterList != null && chapterList.size() > 0){
                for (EduChapter eduChapter : chapterList) {
                    String chapterId = eduChapter.getId();
                    eduChapterService.deleteChapterById(chapterId);
                }
            }
             return true;
        }
        return false;
    }

    @Override
    public PublishInfo selectPublishInfoById(String id) {
        if(StringUtils.isNotBlank(id)){
            PublishInfo publishInfo = eduCourseMapper.selectPublishById(id);
            return publishInfo;
        }
        return null;
    }

    @Override
    public boolean publishCourseById(String id) {
        if(StringUtils.isNotBlank(id)){
            EduCourse eduCourse = new EduCourse();
            eduCourse.setStatus("Normal");
            eduCourse.setId(id);
            int update = eduCourseMapper.updateById(eduCourse);
            if(update > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> selectCourseListByPage(long pageNum, long pageSize) {
        Map<String, Object> map = new HashMap<>();
        Page<EduCourse> coursePage = new Page<>(pageNum,pageSize);
        eduCourseMapper.selectPage(coursePage,null);
        List<EduCourse> records = coursePage.getRecords();
        long size = coursePage.getSize();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        long current = coursePage.getCurrent();
        map.put("hasPrevious",hasPrevious);
        map.put("currentPage",current);
        map.put("hasNext",hasNext);
        map.put("pages",size);
        map.put("courseList",records);

        return map;
    }

    @Override
    public Map<String, Object> selectCourseAndBrotherById(String courseId) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(courseId)) {
            CourseAndBrother courseAndBrother = eduCourseMapper.selectCourseAndBrotherByCourseId(courseId);
            List<ChapterVo> chapterVos = eduChapterService.getAllChapterByCourseId(courseId);
            map.put("brotherInfo",courseAndBrother);
            map.put("chapterList",chapterVos);
        }
        return map;

    }
}
