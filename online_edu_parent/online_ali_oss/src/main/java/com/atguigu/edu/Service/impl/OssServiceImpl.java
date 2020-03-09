package com.atguigu.edu.Service.impl;

import com.atguigu.edu.Service.OssService;
import com.atguigu.edu.oss.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OssUtils ossUtils;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String url = "";
        try {
            InputStream inputStream = multipartFile.getInputStream();
            String originalFilename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            originalFilename = originalFilename+uuid;
            url = ossUtils.fileUpload(originalFilename, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public void deleteFile(String fileName) {
        ossUtils.deleteFile(fileName);
    }


}
