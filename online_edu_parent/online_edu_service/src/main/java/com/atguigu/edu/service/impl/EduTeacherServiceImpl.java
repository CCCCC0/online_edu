package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.mapper.EduTeacherMapper;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.edu.vo.request.QueryTeacherCondition;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author CL
 * @since 2020-03-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeacherMapper eduTeacherMapper;

    @Override
    public void pageByCondition(Page<EduTeacher> eduTeacherPage, QueryTeacherCondition queryTeacherCondition) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = queryTeacherCondition.getName();
        Integer level = queryTeacherCondition.getLevel();
        String gmtCreate = queryTeacherCondition.getGmtCreate();
        String gmtModified = queryTeacherCondition.getGmtModified();

        if(StringUtils.isNotBlank(name)){
            wrapper.like("name",name);
        }
        if(level != 0){
            wrapper.eq("level",level);
        }
        if(StringUtils.isNotBlank(gmtCreate)){
            wrapper.gt("gmt_create",gmtCreate);
        }
        if(StringUtils.isNotBlank(gmtModified)){
            wrapper.lt("gmt_modified",gmtModified);
        }
        eduTeacherMapper.selectPage(eduTeacherPage,wrapper);
    }

}
