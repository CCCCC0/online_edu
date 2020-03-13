package com.atguigu.edu.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : CL
 * @Des : 课程查询条件
 */
@Data
public class QueryCourseCondition {

    @ApiModelProperty(value = "视频状态 Draft未发布  Normal已发布")
    private String status;

    @ApiModelProperty(value = "课程标题")
    private String title;

}
