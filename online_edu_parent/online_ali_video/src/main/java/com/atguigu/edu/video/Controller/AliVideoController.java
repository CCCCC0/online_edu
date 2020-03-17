package com.atguigu.edu.video.Controller;

import com.atguigu.edu.video.Service.VideoService;
import com.atguigu.edu.vo.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/ali/video")
public class AliVideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("upload")
    public RetVal uploadVideo(MultipartFile file){
        String videoId = videoService.uploadVideo(file);
        return RetVal.success().data("videoId",videoId);
    }

    @DeleteMapping("delete/{id}")
    public RetVal deleteVideo(@PathVariable String id){
        videoService.deleteVideoById(id);
        return RetVal.success();
    }

}
