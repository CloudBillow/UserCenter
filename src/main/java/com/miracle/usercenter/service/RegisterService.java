package com.miracle.usercenter.service;

import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.pojo.vo.TokenVO;

/**
 * 用户注册Service
 *
 * @author XieYT
 * @since 2023/03/02 22:40
 */
public interface RegisterService {

    /**
     * 用户名密码注册
     *
     * @param user 用户信息
     */
    TokenVO registerByUsernameAndPassword(UserDTO user);
}
