package com.atguigu.edu.video.util;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import org.apache.commons.lang.StringUtils;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyUtils {

    //账号AK信息请填写(必选)
    private final static String accessKeyId = "LTAI4FfoNTdfHCfzbc2MTnAK";
    //账号AK信息请填写(必选)
    private final static String accessKeySecret = "FLv8TiMmAghGEy7diCAbeneIir6S1i";

    public static void deleteVideo(String videoList) throws ClientException {
        DefaultAcsClient client = VideoUtils.initVodClient(accessKeyId, accessKeySecret);
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoList);
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
    }

    //获取播放凭证函数
    public static String getVideoPlayAuth(String videoId) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        DefaultAcsClient acsClient = VideoUtils.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        String playAuth = "";
        try {
            response = acsClient  .getAcsResponse(request);
            //播放凭证
            playAuth = response.getPlayAuth();
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        return playAuth;
    }

    public static String uploadVideo(String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName,inputStream);
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return response.getVideoId();
    }

    //获取播放地址函数
    public static List<String> getPlayInfo(String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);

        DefaultAcsClient ascClient = VideoUtils.initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        List<String> playUrlList = new ArrayList<>();
        try {
            response = ascClient.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                String playURL = playInfo.getPlayURL();
                if(StringUtils.isNotBlank(playURL)){
                    playUrlList.add(playURL);
                }
            }
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        return playUrlList;
    }
}
