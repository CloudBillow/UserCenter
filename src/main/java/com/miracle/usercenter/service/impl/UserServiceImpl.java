package com.miracle.usercenter.service.impl;

import com.miracle.usercenter.pojo.bo.LoginUserBO;
import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.service.UserService;
import com.miracle.usercenter.util.JwtUtil;
import com.miracle.usercenter.util.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户Service
 *
 * @author XieYT
 * @since 2023/02/26 21:46
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${redis-key.user-center.user.login}")
    private String userLoginKey;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 用户登录
     *
     * @param user 用户登录信息
     * @return token
     */
    @Override
    public String login(UserDTO user) {

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                );

        // 认证
        Authentication authenticate = authenticationManager.authenticate(authentication);

        // 获取用户ID
        Object principal = authenticate.getPrincipal();
        LoginUserBO loginUser = (LoginUserBO) principal;
        String userId = loginUser.getUser().getId().toString();

        // 向Redis中添加用户信息
        redisUtils.set(String.format(userLoginKey, userId), loginUser);

        return JwtUtil.createToken(userId);

    }
}
