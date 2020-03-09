package com.atguigu.edu.controller;

import com.aliyun.oss.common.utils.StringUtils;
import com.atguigu.edu.Service.OssService;
import com.atguigu.edu.vo.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@CrossOrigin
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("upload")
    public RetVal uploadFile(@RequestParam("file") MultipartFile multipartFile){
        if(multipartFile != null){
            String url = ossService.uploadFile(multipartFile);
            return RetVal.success().data("url",url);
        }else{
            return RetVal.error().message("图片格式有误!");
        }
    }

    @PostMapping("delete")
    public RetVal deleteFile(String fileName){
        if(StringUtils.isNullOrEmpty(fileName)) {
            ossService.deleteFile(fileName);
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
}
