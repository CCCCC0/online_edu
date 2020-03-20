package com.atguigu.edu.user.controller;


import com.atguigu.edu.user.service.MemberCenterService;
import com.atguigu.edu.vo.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author CL
 * @since 2020-03-17
 */
@RestController
@RequestMapping("/member/center")
@CrossOrigin
public class MemberCenterController {

    @Autowired
    private MemberCenterService memberCenterService;

    @GetMapping("register/{day}")
    public RetVal getDayRegisterUserData(@PathVariable String day){
        Integer count = memberCenterService.selectRegisterUserInDay(day);
        return RetVal.success().data("count",count);
    }



}

