package com.miracle.usercenter.config;

import com.miracle.usercenter.filter.JwtAuthenticationTokenFilter;
import com.miracle.usercenter.handler.AccessDeniedHandlerImpl;
import com.miracle.usercenter.handler.AuthenticationEntryPointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Security配置类
 *
 * @author XieYT
 * @since 2023/02/26 19:44
 */
@Configuration
public class SecurityConfig {

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;


    /**
     * 密码校验器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置安全拦截机制
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 禁用CSRF，因为我们不为浏览器客户端提供服务。
                .csrf().disable()
                // 确保我们使用无状态会话，会话不会被用来存储用户的状态。
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 允许使用请求匹配器进行访问限制
                .authorizeRequests()
                // 配置登录接口匿名访问（没有认证时可以访问，认证后不可以访问）
                .antMatchers("/user/login").anonymous()
                // 配置hell测试接口任何人都可以访问（不管有没有认证都可以访问）
                .antMatchers().permitAll()
                // 配置其他请求必须认证
                .anyRequest().authenticated();

        http
                // 添加JWT登录授权过滤器
                .addFilterBefore(
                        jwtAuthenticationTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

        return http.build();

    }

}
