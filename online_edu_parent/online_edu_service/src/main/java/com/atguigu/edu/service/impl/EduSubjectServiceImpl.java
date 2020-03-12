package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.mapper.EduSubjectMapper;
import com.atguigu.edu.service.EduSubjectService;
import com.atguigu.edu.vo.response.EduSubjectVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author CL
 * @since 2020-03-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectMapper eduSubjectMapper;

    @Override
    public List<EduSubjectVO> selectAllSub() {
        //放置所有的一级分类
        Map<String,EduSubjectVO> eduInfo = new HashMap<>();
        //返回所有的父List
        List<EduSubjectVO> eduInfos = new ArrayList<>();
        //DB中查询出所有数据
        List<EduSubject> subjectList = eduSubjectMapper.selectList(null);
        new ArrayList<EduSubjectVO>();
        for (EduSubject eduSubject : subjectList) {
            if("0".equals(eduSubject.getParentId())){
                EduSubjectVO vo = new EduSubjectVO();
                BeanUtils.copyProperties(eduSubject,vo);
                eduInfo.put(vo.getId(),vo);
                eduInfos.add(vo);
            }
        }
        //找爹
        Set<String> strings = eduInfo.keySet();
        for (String string : strings) {
            EduSubjectVO subjectVO = eduInfo.get(string);
            for (EduSubject eduSubject : subjectList) {
                if(string.equals(eduSubject.getParentId())){
                    EduSubjectVO son = new EduSubjectVO();
                    BeanUtils.copyProperties(eduSubject,son);
                    subjectVO.getChildren().add(son);
                }
            }
        }
        return eduInfos;
    }

    @Override
    public List<String> insertXlsToDB(MultipartFile file) throws IOException {
        //获取输入流读取xls文件中内容
        InputStream inputStream = file.getInputStream();
        //创建workBook
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        //获取sheet的数量
        int sheets = workbook.getNumberOfSheets();
        List<String> errorInfo = new ArrayList<>();
        for (int i = 0; i < sheets; i++) {
            HSSFSheet sheet = workbook.getSheetAt(i);
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                //定义每行的父Id
                String parentId = "0";
                HSSFRow row = sheet.getRow(j);
                for (int k = row.getFirstCellNum(); k <= row.getLastCellNum(); k++) {
                    HSSFCell cell = row.getCell(k, Row.RETURN_BLANK_AS_NULL);
                    if (cell != null) {
                        Object cellValue = null;
                        //获取内容进行插入
                        int type = cell.getCellType();
                        switch (type) {
                            case Cell.CELL_TYPE_STRING:
                                cellValue = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                cellValue = cell.getBooleanCellValue();
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                cellValue = cell.getNumericCellValue();
                                break;
                        }
                        //在数据库中进行查询 该内容是否存在
                        EduSubject e = isExisted(parentId, cellValue + "");
                        if(e == null){
                            //可以插入
                            EduSubject eduSubject = new EduSubject();
                            eduSubject.setParentId(parentId);
                            eduSubject.setTitle(cellValue + "");
                            eduSubject.setSort(1);
                            eduSubjectMapper.insert(eduSubject);
                            parentId = eduSubject.getId();
                        }
                        if(e != null){
                            //当前parentId作为下一个的父节点
                            parentId = e.getId();
                        }
                    }else{
                        errorInfo.add("第" + (i+1) + "sheet的" + "第" + (j+1) + "行的" + "第" + (k+1)  + "列为空！");
                        continue;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean deleteSubjectById(String id) {
        if(StringUtils.isNotBlank(id)) {
            EduSubject eduSubject = eduSubjectMapper.selectById(id);
            if(eduSubject != null){
                String parentId = eduSubject.getParentId();
                if("0".equals(parentId)){
                    //为父节点 - 需要查询下面是否有子节点
                    QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
                    wrapper.eq("parent_id",id);
                    List<EduSubject> eduSubjects = eduSubjectMapper.selectList(wrapper);
                    if(eduSubjects != null && eduSubjects.size() > 0){
                        return false;
                    }
                }
                    int row = eduSubjectMapper.deleteById(id);
                    if(row > 0){
                        return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean saveLevelOneSub(EduSubject eduSubject) {
        if(eduSubject != null){
            String title = eduSubject.getTitle();
            EduSubject existed = isExisted("0", title);
            if(existed == null){
                //判断是否为父节点
                    //进行添加
                    eduSubject.setSort(1);
                    eduSubject.setParentId("0");
                    eduSubjectMapper.insert(eduSubject);
                    return true;
                }
            }
        return false;
    }

    @Override
    public boolean saveLevelTwoSub(EduSubject eduSubject) {
        if(eduSubject != null){
            String parentId = eduSubject.getParentId();
            String title = eduSubject.getTitle();
            EduSubject existed = isExisted(parentId, title);
            if(existed == null && StringUtils.isNotBlank(parentId) && !"0".equals(parentId)){
                //进行子节点的插入
                eduSubject.setSort(1);
                eduSubjectMapper.insert(eduSubject);
                return true;
            }
        }
        return false;
    }


    //查询是否存在 true可以插入  false不能插入
    public EduSubject isExisted(String parentId,String subjectName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id",parentId);
        EduSubject eduSubject = eduSubjectMapper.selectOne(wrapper);
        return eduSubject;
    }
}
