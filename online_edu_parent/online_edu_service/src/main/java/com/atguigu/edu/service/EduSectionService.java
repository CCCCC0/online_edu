package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduSection;
import com.atguigu.edu.vo.response.SectionVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程小节 服务类
 * </p>
 *
 * @author CL
 * @since 2020-03-13
 */
public interface EduSectionService extends IService<EduSection> {

    boolean addEduSection(EduSection eduSection);

    SectionVo getSectionVOById(String id);

    boolean updateEduSection(SectionVo sectionVo);

    boolean deleteSectionById(String id);
}
