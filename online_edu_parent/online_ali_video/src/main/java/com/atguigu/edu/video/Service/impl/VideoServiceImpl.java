package com.atguigu.edu.video.Service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.atguigu.edu.video.Service.VideoService;
import com.atguigu.edu.video.util.MyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VideoServiceImpl implements VideoService {

    @Override
    public String uploadVideo(MultipartFile multipartFile) {
        String videoId = "";
        if(multipartFile != null){
            try {
            String originalFilename = multipartFile.getOriginalFilename();
            String name = originalFilename.substring(0,originalFilename.indexOf("."));
            //获取视频Id
            InputStream inputStream = multipartFile.getInputStream();
            videoId = MyUtils.uploadVideo(name, originalFilename,inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return videoId;
    }

    @Override
    public void deleteVideoById(String id) {
        if (StringUtils.isNotBlank(id)){
            try {
                MyUtils.deleteVideo(id);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getPlayerAuthById(String videoId) {
        String authCode = "";
        if(StringUtils.isNotBlank(videoId)){
            try {
                authCode = MyUtils.getVideoPlayAuth(videoId);
                if(StringUtils.isNotBlank(authCode)){
                    return authCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return authCode;
    }
}
