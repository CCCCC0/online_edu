package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduSection;

import com.atguigu.edu.mapper.EduSectionMapper;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.edu.service.EduVideoService;
import com.atguigu.edu.vo.response.SectionVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程小节 服务实现类
 * </p>
 *
 * @author CL
 * @since 2020-03-13
 */
@Service
public class EduSectionServiceImpl extends ServiceImpl<EduSectionMapper, EduSection> implements EduSectionService {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduSectionMapper sectionMapper;

    @Override
    public boolean addEduSection(EduSection eduSection) {
        if(eduSection != null){
            eduSection.setStatus("Draft");
            eduSection.setSize(new Long(0));
            eduSection.setVersion(new Long(1));
            int insert = sectionMapper.insert(eduSection);
            if(insert > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public SectionVo getSectionVOById(String id) {
        SectionVo sectionVo = new SectionVo();
        if(StringUtils.isNotBlank(id)){
            EduSection eduSection = sectionMapper.selectById(id);
            BeanUtils.copyProperties(eduSection,sectionVo);
        }
        return sectionVo;
    }

    @Override
    public boolean updateEduSection(SectionVo sectionVo) {
        if(sectionVo != null){
            EduSection eduSection = new EduSection();
            BeanUtils.copyProperties(sectionVo,eduSection);
            eduSection.setStatus("Draft");
            eduSection.setSize(new Long(0));
            int update = sectionMapper.updateById(eduSection);
            if(update > 0){
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteSectionById(String id) {
        if(StringUtils.isNotBlank(id)) {
            EduSection eduSection = sectionMapper.selectById(id);
            String videoSourceId = eduSection.getVideoSourceId();
            sectionMapper.deleteById(id);
            if(StringUtils.isNotBlank(videoSourceId)){
                //调用video的服务进行删除
                eduVideoService.deleteVideo(videoSourceId);
            }
            return true;
        }
        return false;
    }
}
