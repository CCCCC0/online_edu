package com.atguigu.edu.handler.exceptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("自定义异常类")
public class EduException extends RuntimeException{
    //swagger注解
    @ApiModelProperty(value = "异常状态码")
    private Integer code;
    @ApiModelProperty(value = "异常信息")
    private String message;
}
