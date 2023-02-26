package com.miracle.usercenter.util;

import javax.servlet.http.HttpServletResponse;

/**
 * 将字符串渲染到页面
 *
 * @author XieYT
 * @since 2023/02/26 18:02
 */
public class WebUtils {

    /**
     * 将字符串渲染到页面
     *
     * @param response response
     * @param string   字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.reset();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
