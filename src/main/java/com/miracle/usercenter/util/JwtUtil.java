package com.miracle.usercenter.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * 快速生成token，解析token，校验token
 *
 * @author Miracle
 */
@SuppressWarnings("unused")
@Slf4j
public class JwtUtil {
    /**
     * Token过期时间必须大于生效时间
     */
    private static final Long TOKEN_EXPIRE_TIME = 60 * 1 * 1000L;
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 60 * 5 * 1000L;
    /**
     * Token加密解密的密码
     */
    private static final String TOKEN_SECRET = "miracle.user.center";
    /**
     * 加密类型 三个值可取 HS256  HS384  HS512
     */
    private static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;
    /**
     * 添加一个前缀
     */
    private static final String JWT_SEPARATOR = "Bearer ";
    /**
     * token生效时间(默认是从当前开始生效)
     * 默认：new Date(System.currentTimeMillis() + START_TIME)
     */
    private static final Long START_TIME = 0L;
    /**
     * token在什么时间之前是不可用的（默认从当前时间）
     * 默认：new Date(System.currentTimeMillis() + BEFORE_TIME)
     */
    private static final Long BEFORE_TIME = 0L;

    private static Key generateKey() {
        // 将密码转换为字节数组
        byte[] bytes = Base64.decodeBase64(TOKEN_SECRET);
        // 根据指定的加密方式，生成密钥
        return new SecretKeySpec(bytes, JWT_ALG.getJcaName());
    }

    /**
     * 创建token
     *
     * @param sub    主题
     * @param aud    受众
     * @param jti    编号
     * @param iss    签发人
     * @param claims 自定义信息
     * @return 加密后的token字符串
     */
    public static String createToken(String sub, String aud, String jti, String iss, Map<String, Object> claims) {
        final JwtBuilder builder = Jwts.builder();
        if (MapUtils.isNotEmpty(claims)) {
            builder.setClaims(claims);
        }
        String token = builder
                .signWith(JWT_ALG, generateKey())
                .setSubject(sub)
                .setAudience(aud)
                .setId(jti)
                .setIssuer(iss)
                .setNotBefore(new Date(System.currentTimeMillis() + BEFORE_TIME))
                .setIssuedAt(new Date(System.currentTimeMillis() + START_TIME))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
                .compact();
        return JWT_SEPARATOR + token;
    }

    /**
     * 创建token
     *
     * @param sub    主题
     * @param aud    受众
     * @param claims 自定义信息
     * @return 加密后的token字符串
     */
    public static String createToken(String sub, String aud, Map<String, Object> claims) {
        return createToken(sub, aud, new Date().toString(), null, claims);
    }

    /**
     * 创建token
     *
     * @param sub    主题
     * @param claims 自定义信息
     * @return 加密后的token字符串
     */
    public static String createToken(String sub, Map<String, Object> claims) {
        return createToken(sub, null, claims);
    }

    /**
     * 创建token
     *
     * @param sub token所面向的用户
     * @return token字符串
     */
    public static String createToken(String sub) {
        return createToken(sub, null);
    }

    /**
     * 解析token
     * 可根据Jws<Claims>   获取  header|body|getSignature三部分数据
     *
     * @param token token字符串
     * @return Jws
     */
    public static Jws<Claims> parseToken(String token) {
        // 移除 token 前的"XXX#"字符串
        token = StringUtils.substringAfter(token, JWT_SEPARATOR);
        // 解析 token 字符串
        return Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
    }

    /**
     * 校验token,校验是否是本服务器的token
     *
     * @param token token字符串
     * @return boolean
     */
    public static Boolean checkToken(String token) {
        return parseToken(token).getBody() != null;
    }

    /**
     * 根据sub判断token
     *
     * @param token token字符串
     * @param sub   面向的用户
     * @return boolean
     */
    public static Boolean checkToken(String token, String sub) {
        return parseToken(token).getBody().getSubject().equals(sub);
    }

    /**
     * 判断token是否过期
     *
     * @param token token字符串
     * @return boolean
     */
    public static Boolean isTokenExpired(String token) {
        try {
            return parseToken(token).getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 生成刷新token
     *
     * @param userId 用户ID
     * @return 刷新token
     */
    public static String createRefreshToken(String userId) {
        return JWT_SEPARATOR + Jwts.builder()
                .signWith(JWT_ALG, generateKey())
                .setSubject(userId)
                .setNotBefore(new Date(System.currentTimeMillis() + BEFORE_TIME))
                .setIssuedAt(new Date(System.currentTimeMillis() + START_TIME))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .compact();
    }
}