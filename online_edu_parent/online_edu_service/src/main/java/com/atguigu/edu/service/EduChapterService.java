package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.vo.response.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author CL
 * @since 2020-03-13
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getAllChapterByCourseId(String courseId);

    boolean insertChapterToDB(EduChapter eduChapter);

    boolean updateChapterToDB(EduChapter eduChapter);

    ChapterVo getChapterById(String id);
}
