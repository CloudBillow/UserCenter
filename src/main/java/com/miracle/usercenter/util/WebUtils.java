package com.miracle.usercenter.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

/**
 * 将字符串渲染到页面
 *
 * @author XieYT
 * @since 2023/02/26 18:02
 */
@Slf4j
public class WebUtils {

    /**
     * 将字符串渲染到页面
     *
     * @param response response
     * @param body     响应体
     */
    public static void renderString(HttpServletResponse response, String body) {
        try {
            response.reset();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(body);
        } catch (Exception e) {
            log.error("renderString异常", e);
        }
    }
}
