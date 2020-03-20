package com.atguigu.edu.user.service;

import com.atguigu.edu.user.entity.MemberCenter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author CL
 * @since 2020-03-17
 */
public interface MemberCenterService extends IService<MemberCenter> {

    Integer selectRegisterUserInDay(String day);

    MemberCenter selectMemberByOpenId(String open_id);
}
