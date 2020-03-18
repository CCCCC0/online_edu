package com.atguigu.edu.statistic.service;

import com.atguigu.edu.vo.response.RetVal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("EDU-USER")
public interface UserService {
    @GetMapping("/member/center/register/{day}")
    public RetVal getDayRegisterUserData(@PathVariable String day);
}
