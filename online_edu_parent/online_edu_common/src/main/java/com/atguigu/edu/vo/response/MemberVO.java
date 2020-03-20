package com.atguigu.edu.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberVO {

    @ApiModelProperty(value = "性别 1 女，2 男")
    private double sex;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "会员id")
    private String id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

}
