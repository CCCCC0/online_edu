package com.atguigu.edu.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ChapterVo {
    @ApiModelProperty(value = "章节ID")
    private String id;
    @ApiModelProperty(value = "章节名称")
    private String title;
    @ApiModelProperty(value = "显示排序")
    private Integer sort;
    //章节里面拥有小节信息
    private List<SectionVo> children=new ArrayList<>();
}
