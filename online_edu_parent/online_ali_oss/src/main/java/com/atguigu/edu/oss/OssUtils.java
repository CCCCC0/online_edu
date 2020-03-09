package com.atguigu.edu.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Component
public class OssUtils {

    private final String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    private final String accessKeyId = "LTAI4FfoNTdfHCfzbc2MTnAK";
    private final String accessKeySecret = "FLv8TiMmAghGEy7diCAbeneIir6S1i";
    private final String bucketName = "psy-online-edu";

    public String fileUpload(String fileName,InputStream inputStream) throws FileNotFoundException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, "pic/" + fileName, inputStream);
        String url = "https://"+bucketName+"."+endpoint+"/pic/"+fileName;
        ossClient.shutdown();
        return url;
    }

    public void deleteFile(String fileName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileName);
        ossClient.shutdown();
    }

}
