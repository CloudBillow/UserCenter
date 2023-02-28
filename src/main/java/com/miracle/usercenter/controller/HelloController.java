package com.miracle.usercenter.controller;

import com.miracle.usercenter.common.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author XieYT
 * @since 2023/02/27 21:08
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('admin1')")
    public Result<String> hello() {
        return Result.success("hello");
    }
}
