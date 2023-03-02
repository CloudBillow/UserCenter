package com.miracle.usercenter.controller;

import com.miracle.usercenter.common.Result;
import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.pojo.vo.TokenVO;
import com.miracle.usercenter.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 注册Controller
 *
 * @author XieYT
 * @since 2023/03/02 22:30
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    /**
     * 用户名密码注册
     */
    @PostMapping("/register")
    public Result<TokenVO> register(@RequestBody UserDTO user) {
        TokenVO token = registerService.registerByUsernameAndPassword(user);
        return Result.success(token, "注册成功");
    }
}
