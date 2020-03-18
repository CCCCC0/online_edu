package com.atguigu.edu.user.mapper;

import com.atguigu.edu.user.entity.MemberCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author CL
 * @since 2020-03-17
 */
public interface MemberCenterMapper extends BaseMapper<MemberCenter> {
    Integer selectRegisterUserInDay(String day);
}
