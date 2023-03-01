package com.miracle.usercenter.service;

import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.pojo.vo.TokenVO;

/**
 * 用户Service
 *
 * @author XieYT
 * @since 2023/02/26 21:46
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param userDTO 用户登录信息
     * @return token和refreshToken
     */
    TokenVO login(UserDTO userDTO);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 刷新accessToken
     *
     * @param accessToken  原accessToken
     * @param refreshToken refreshToken
     * @return 新accessToken
     */
    String refreshToken(String accessToken, String refreshToken);
}
