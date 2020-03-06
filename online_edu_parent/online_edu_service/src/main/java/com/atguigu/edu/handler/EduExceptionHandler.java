package com.atguigu.edu.handler;

import com.atguigu.edu.handler.exceptions.EduException;
import com.atguigu.edu.vo.response.RetVal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Des ：统一异常处理器
 * @Author ：CL
 */
@ControllerAdvice
public class EduExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RetVal exceptionHandler(Exception e){
        return RetVal.error().message("全局异常");
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public RetVal exceptionOtherHandler(NullPointerException e){
        return RetVal.error().message("特殊异常");
    }

    @ResponseBody
    @ExceptionHandler(EduException.class)
    public RetVal exceptionEduHandler(EduException e){
        return RetVal.error().message("自定义异常");
    }
}
