package com.miracle.usercenter.filter;

import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.common.UserCenterException;
import com.miracle.usercenter.pojo.bo.LoginUserBO;
import com.miracle.usercenter.util.JwtUtil;
import com.miracle.usercenter.util.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * JWT登录授权过滤器
 *
 * @author XieYT
 * @since 2023/02/26 22:51
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisUtils redisUtils;
    @Value("${redis-key.user-center.user.login}")
    private String userLoginKey;
    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取请求头中的token
        String token = request.getHeader("token");

        // 如果请求头中没有token，则return，继续执行下一个过滤器
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token, 获取用户ID
        Jws<Claims> claimsJws;
        try {
            claimsJws = JwtUtil.parseToken(token);
        } catch (ExpiredJwtException e) {
            handlerExceptionResolver.resolveException(request, response, null,
                    new UserCenterException(CODE.USER_NOT_LOGIN));
            return;
        } catch (Exception e) {
            e.printStackTrace();
            handlerExceptionResolver.resolveException(request, response, null,
                    new UserCenterException(CODE.TOKEN_ERROR));
            return;
        }
        String userId = claimsJws.getBody().getSubject();

        // 从Redis中获取用户信息
        LoginUserBO loginUser = redisUtils.get(
                String.format(userLoginKey, userId),
                LoginUserBO.class
        );

        System.out.println("loginUser = " + loginUser);

        // 将用户信息放入SecurityContext中
        if (Objects.isNull(loginUser)) {
            throw new UserCenterException(CODE.TOKEN_ERROR);
        }

        // 将用户信息放入SecurityContext中
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginUser,
                null,
                // TODO 获取权限信息
                loginUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
}
