package com.atguigu.edu.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PublishInfo {
    /*
    courseId :
    cover : "",     //图片路径
    courseName : "",  //课程名称
    parentSubject : "", //所属一级分类
    childSubject : "",  //所属二级分类
    teacherName : "",  //讲师名称
    price : "",      //价格
    courseNum : ""   //课程数目
    */
    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "课程标题")
    private String courseName;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "父级分类名称")
    private String parentSubject;

    @ApiModelProperty(value = "子级分类名称")
    private String childSubject;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "课程数目")
    private Integer courseNum;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

}
