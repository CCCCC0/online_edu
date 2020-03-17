package com.atguigu.edu.service;

import com.atguigu.edu.vo.response.RetVal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "EDU-VIDEO")
public interface EduVideoService {

    @PostMapping("/ali/video/upload")
    public RetVal uploadVideo(MultipartFile file);

    @DeleteMapping("/ali/video/delete/{id}")
    public RetVal deleteVideo(@PathVariable String id);

}
