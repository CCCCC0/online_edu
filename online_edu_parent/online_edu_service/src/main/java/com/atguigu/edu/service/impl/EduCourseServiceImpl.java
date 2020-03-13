package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduCourseDescription;
import com.atguigu.edu.mapper.EduCourseDescriptionMapper;
import com.atguigu.edu.mapper.EduCourseMapper;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.vo.request.QueryCourseCondition;
import com.atguigu.edu.vo.response.EduCourseVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @since 2020-03-12
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

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
             //不做判断
             eduCourseMapper.deleteById(id);
             eduCourseDescriptionMapper.deleteById(id);
             return true;
        }
        return false;
    }
}
