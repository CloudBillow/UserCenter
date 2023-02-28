package com.miracle.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.mapper.UserMapper;
import com.miracle.usercenter.pojo.bo.LoginUserBO;
import com.miracle.usercenter.pojo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 用户详情Service
 *
 * @author XieYT
 * @since 2023/02/26 18:37
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名定位用户。
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户名未找到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 从数据库中获取用户信息
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );

        // 如果没有查询到用户就抛出异常
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(CODE.USERNAME_OR_PASSWORD_ERROR.getMessage());
        }

        // TODO 查询用户权限信息
        List<String> permissions = new ArrayList<>(
                Arrays.asList("test", "admin")
        );

        return LoginUserBO.builder()
                .user(user)
                .permissions(permissions)
                .build();
    }
}
