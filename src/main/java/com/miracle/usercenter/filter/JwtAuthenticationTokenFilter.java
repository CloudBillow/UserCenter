package com.miracle.usercenter.filter;

import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.common.UserCenterException;
import com.miracle.usercenter.pojo.bo.LoginUserBO;
import com.miracle.usercenter.util.JwtUtil;
import com.miracle.usercenter.util.RedisUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisUtils redisUtils;
    @Value("${redis-key.user-center.user.login}")
    private String userLoginKey;
    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获取请求头中的Refresh-Token
        String refreshToken = request.getHeader("Refresh-Token");

        // 获取请求头中的Access-Token
        String accessToken = request.getHeader("Access-Token");

        // 如果请求头中没有token，则return，继续执行下一个过滤器
        if (StringUtils.isEmpty(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 如果请求头中有Refresh-Token，则进行刷新Token的操作
        if (StringUtils.isNotBlank(refreshToken)) {

            // accessToken中获取用户ID
            String accessId;
            // refreshToken中获取用户ID
            String refreshId;

            try {
                accessId = JwtUtil.parseToken(accessToken).getBody().getSubject();
            } catch (ExpiredJwtException e) {
                accessId = e.getClaims().getSubject();
            }

            try {
                refreshId = JwtUtil.parseToken(accessToken).getBody().getSubject();
            } catch (ExpiredJwtException e) {
                handlerExceptionResolver.resolveException(request, response, null,
                        new UserCenterException(CODE.USER_NOT_LOGIN));
                return;
            }

            // 如果两个Token不属于同一个用户，则退出登录
            if (Objects.equals(accessId, refreshId)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 如果Refresh-Token已经过期，则退出登录
            handlerExceptionResolver.resolveException(request, response, null,
                    new UserCenterException(CODE.USER_NOT_LOGIN));
            return;
        }


        // 如果请求头中没有Refresh-Token，则进行登录授权的操作
        Jws<Claims> claimsJws = null;

        CODE code = null;
        try {
            // 解析token, 获取用户ID
            claimsJws = JwtUtil.parseToken(accessToken);
        } catch (ExpiredJwtException e) {
            code = CODE.TOKEN_EXPIRED;
        } catch (
                MalformedJwtException |
                IncorrectClaimException |
                SignatureException e
        ) {
            code = CODE.TOKEN_INVALID;
        } catch (Exception e) {
            log.error("token解析失败", e);
            code = CODE.TOKEN_ERROR;
        }

        // 如果有异常，则抛出异常
        if (code != null) {
            handlerExceptionResolver.resolveException(request, response, null,
                    new UserCenterException(code));
            return;
        }

        String userId = claimsJws.getBody().getSubject();

        // 从Redis中获取用户信息
        LoginUserBO loginUser = redisUtils.get(
                String.format(userLoginKey, userId),
                LoginUserBO.class
        );

        // 将用户信息放入SecurityContext中
        if (Objects.isNull(loginUser)) {
            // 如果没有查询到用户信息，则说明用户未登录
            handlerExceptionResolver.resolveException(request, response, null,
                    new UserCenterException(CODE.USER_NOT_LOGIN));
            return;
        }

        // 将用户信息放入SecurityContext中
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginUser,
                null,
                // 获取权限信息
                loginUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);


    }
}
