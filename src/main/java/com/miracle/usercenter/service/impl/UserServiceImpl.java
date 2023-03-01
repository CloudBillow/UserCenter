package com.miracle.usercenter.service.impl;

import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.common.UserCenterException;
import com.miracle.usercenter.pojo.bo.LoginUserBO;
import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.pojo.vo.TokenVO;
import com.miracle.usercenter.service.UserService;
import com.miracle.usercenter.util.JwtUtil;
import com.miracle.usercenter.util.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public TokenVO login(UserDTO user) {

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

        return TokenVO.builder()
                .accessToken(JwtUtil.createToken(userId))
                .refreshToken(JwtUtil.createRefreshToken(userId))
                .build();

    }

    /**
     * 用户登出
     */
    @Override
    public void logout() {

        // 获取SecurityContext中的用户ID
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder
                        .getContext().getAuthentication();

        LoginUserBO loginUser = (LoginUserBO) authentication.getPrincipal();

        Integer userId = loginUser.getUser().getId();

        // 删除Redis中的用户信息
        redisUtils.delete(String.format(userLoginKey, userId));

    }

    /**
     * 刷新accessToken
     *
     * @param accessToken  原accessToken
     * @param refreshToken refreshToken
     * @return 新accessToken
     */
    @Override
    public String refreshToken(String accessToken, String refreshToken) {

        // 解析过期的accessToken
        Jws<Claims> claimsJws = JwtUtil.parseToken(accessToken);
        String userId = claimsJws.getBody().getSubject();

        // 如果refreshToken过期，则退出登录
        if (JwtUtil.isTokenExpired(refreshToken)) {
            // 删除缓存中的登录用户信息
            redisUtils.delete(String.format(userLoginKey, userId));
            throw new UserCenterException(CODE.USER_NOT_LOGIN);
        }

        // 判断refreshToken是否与accessToken匹配
        Boolean ifMatching = JwtUtil.checkToken(accessToken, userId);

        if (!ifMatching) {
            throw new UserCenterException(CODE.USER_NOT_LOGIN);
        }

        // 刷新accessToken
        return JwtUtil.createToken(userId);
    }
}
