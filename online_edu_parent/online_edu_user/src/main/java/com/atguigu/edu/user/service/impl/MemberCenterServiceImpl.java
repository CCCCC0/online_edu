package com.atguigu.edu.user.service.impl;

import com.atguigu.edu.user.entity.MemberCenter;
import com.atguigu.edu.user.mapper.MemberCenterMapper;
import com.atguigu.edu.user.service.MemberCenterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author CL
 * @since 2020-03-17
 */
@Service
public class MemberCenterServiceImpl extends ServiceImpl<MemberCenterMapper, MemberCenter> implements MemberCenterService {

    @Autowired
    private MemberCenterMapper memberCenterMapper;



    @Override
    public Integer selectRegisterUserInDay(String day) {
        if(StringUtils.isNotBlank(day)){
            Integer integer = memberCenterMapper.selectRegisterUserInDay(day);
            return integer;
        }
        return null;
    }

    @Override
    public MemberCenter selectMemberByOpenId(String open_id) {
            QueryWrapper<MemberCenter> centerQueryWrapper = new QueryWrapper<>();
            QueryWrapper<MemberCenter> wrapper = centerQueryWrapper.eq("openid", open_id);
            MemberCenter memberCenter = memberCenterMapper.selectOne(wrapper);
            return memberCenter;
    }

}
