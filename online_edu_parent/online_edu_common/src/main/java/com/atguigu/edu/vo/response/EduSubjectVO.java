package com.atguigu.edu.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EduSubjectVO {

    @ApiModelProperty(value = "课程类别ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    private List<EduSubjectVO> children = new ArrayList<>();

}
