package com.atguigu.edu.Service;

import org.springframework.web.multipart.MultipartFile;


public interface OssService {

    String uploadFile(MultipartFile multipartFile);
    void deleteFile(String fileName);
}
