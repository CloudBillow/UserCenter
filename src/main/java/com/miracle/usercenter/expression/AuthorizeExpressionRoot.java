package com.miracle.usercenter.expression;

import com.miracle.usercenter.pojo.bo.LoginUserBO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 权限表达式根对象
 *
 * @author XieYT
 * @since 2023/03/01 21:40
 */
@SuppressWarnings("unused")
@Component("Auth")
public class AuthorizeExpressionRoot {

    // 获取当前用户的权限信息

    /**
     * 判断用户是否有某个权限
     *
     * @param permission 权限Key
     * @return 有权限返回true，否则返回false
     */
    public boolean hasPermission(String permission) {
        LoginUserBO loginUser = (LoginUserBO) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        // 判断用户权限集合中是否存在permission
        return loginUser.getPermissions().contains(permission);
    }
}

