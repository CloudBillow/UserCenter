package com.miracle.usercenter.service;

import com.miracle.usercenter.pojo.dto.UserDTO;

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
     * @return token
     */
    String login(UserDTO userDTO);
}
