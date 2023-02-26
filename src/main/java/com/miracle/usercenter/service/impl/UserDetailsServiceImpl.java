package com.miracle.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.common.UserCenterException;
import com.miracle.usercenter.mapper.UserMapper;
import com.miracle.usercenter.pojo.bo.LoginUserBO;
import com.miracle.usercenter.pojo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户Service
 *
 * @author XieYT
 * @since 2023/02/26 18:37
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 从数据库中获取用户信息
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );

        // 如果没有查询到用户就抛出异常
        if (Objects.isNull(user)) {
            throw new UserCenterException(CODE.USERNAME_OR_PASSWORD_ERROR);
        }

        // TODO 查询用户权限信息

        return LoginUserBO.builder().user(user).build();
    }
}
