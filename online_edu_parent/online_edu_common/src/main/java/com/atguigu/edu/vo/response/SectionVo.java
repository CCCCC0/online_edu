package com.atguigu.edu.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SectionVo {
    @ApiModelProperty(value = "小节ID")
    private String id;

    @ApiModelProperty(value = "小节名称")
    private String title;

    @ApiModelProperty(value = "章节ID")
    private String chapterId;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "是否可以试听：0免费 1收费")
    private Boolean isFree;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;

    @ApiModelProperty(value = "冗余字段")
    private String videoOriginalName;
}
