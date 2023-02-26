package com.miracle.usercenter.controller;

import com.miracle.usercenter.common.Result;
import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author XieYT
 * @since 2023/02/26 16:39
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<String> hello(@RequestBody UserDTO user) {
        String token = userService.login(user);
        return Result.success(token);
    }

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("hello");
    }
}
