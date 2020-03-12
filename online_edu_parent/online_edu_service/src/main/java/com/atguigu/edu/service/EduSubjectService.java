package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.vo.response.EduSubjectVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author CL
 * @since 2020-03-09
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<EduSubjectVO> selectAllSub();

    List<String> insertXlsToDB(MultipartFile multipartFile) throws IOException;

    boolean deleteSubjectById(String id);

    boolean saveLevelOneSub(EduSubject eduSubject);

    boolean saveLevelTwoSub(EduSubject eduSubject);
}
