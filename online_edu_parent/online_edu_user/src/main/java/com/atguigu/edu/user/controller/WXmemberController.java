package com.atguigu.edu.user.controller;

import com.atguigu.edu.user.entity.MemberCenter;
import com.atguigu.edu.user.service.MemberCenterService;
import com.atguigu.edu.user.utils.HttpClientUtils;
import com.atguigu.edu.user.utils.JwtUtils;
import com.atguigu.edu.vo.response.MemberVO;
import com.atguigu.edu.vo.response.RetVal;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WXmemberController {

    @Autowired
    private MemberCenterService memberCenterService;

    private final String appid = "wxed9954c01bb89b47";
    private final String app_secret = "a7482517235173ddb4083788de60b90e";
    //微信回调地址 - 已经配置好的 内网穿透
    private final String redirect_url = "http://guli.shop/api/ucenter/wx/callback";

    @RequestMapping("login")
    public String getCode() throws UnsupportedEncodingException {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对url进行编码
        String encode = URLEncoder.encode(redirect_url, "utf-8");
        //c.该参数可用于防止csrf攻击（跨站请求伪造攻击）
        String state = "atguigu";
        String url = String.format(baseUrl, appid, encode, state);
        return "redirect:" + url;
    }

    @GetMapping("callback")  //携带token跳转到首页
    public String callback(String code,String state) throws Exception {
        //获取code后 使用code和app_id和app_secret 获取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String formatUrl = String.format(baseAccessTokenUrl, appid, app_secret, code);
        //通过httpClient请求微信端获取accessToken
        String tokenInfo = HttpClientUtils.get(formatUrl);
        //使用Gosn将字符串转为Map
        Gson gson = new Gson();
        HashMap tokenMap = gson.fromJson(tokenInfo, HashMap.class);
        String openid = (String) tokenMap.get("openid");
        String access_token = (String) tokenMap.get("access_token");
        //通过access_token-openId获取用户内容
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        String userUrl = String.format(userInfoUrl, access_token, openid);
        String userInfoJson = HttpClientUtils.get(userUrl);
        //将用户信息转换为map
        HashMap userMap = gson.fromJson(userInfoJson, HashMap.class);
        String open_id = (String) userMap.get("openid");
        String nickname = (String) userMap.get("nickname");
        //double sex = (double) userMap.get("sex");
        String headimgurl = (String) userMap.get("headimgurl");
        //到数据库中进行搜索-看是否已有该账号
        //如果没有 则进行保存
        String token = "";
        //根据openid判断该用户是否已经存在
        MemberCenter existMemberCenter= memberCenterService.selectMemberByOpenId(openid);
        if(existMemberCenter==null) {
            //保存个人基本信息
            existMemberCenter = new MemberCenter();
            existMemberCenter.setNickname(nickname);
            existMemberCenter.setAvatar(headimgurl);
            existMemberCenter.setOpenid(openid);
            memberCenterService.save(existMemberCenter);
        }
        token = JwtUtils.geneJsonWebToken(existMemberCenter);
        return "redirect:http://127.0.0.1:3000?token=" + token;
    }

    @GetMapping("token/{token}")
    @ResponseBody
    public RetVal getMemberInfo(@PathVariable String token){
        Claims claims = JwtUtils.checkJWT(token);
        String id = (String) claims.get("id");
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");

        MemberVO memberVO = new MemberVO();
        memberVO.setId(id);
        memberVO.setNickname(nickname);
        memberVO.setAvatar(avatar);
        return RetVal.success().data("memberInfo",memberVO);
    }
}
