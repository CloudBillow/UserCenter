package com.miracle.usercenter.controller;

import com.miracle.usercenter.common.Result;
import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.pojo.vo.TokenVO;
import com.miracle.usercenter.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户Controller
 *
 * @author XieYT
 * @since 2023/02/26 16:39
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<TokenVO> hello(@RequestBody UserDTO user) {
        TokenVO token = userService.login(user);
        return Result.success(token);
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public Result<String> logout() {
        userService.logout();
        return Result.success("注销成功");
    }

    /**
     * 刷新Token
     */
    @GetMapping("/refreshToken")
    public Result<String> refreshToken(
            @RequestHeader("Access-Token") String accessToken,
            @RequestHeader("Refresh-Token") String refreshToken) {
        String newToken = userService.refreshToken(accessToken, refreshToken);
        return Result.success(newToken);
    }
}
