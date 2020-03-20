package com.atguigu.edu.video.Service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    String uploadVideo(MultipartFile multipartFile);

    void deleteVideoById(String id);

    String getPlayerAuthById(String videoId);
}
