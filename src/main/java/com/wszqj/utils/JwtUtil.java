package com.wszqj.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.Date;
import java.util.Map;

import static com.wszqj.constant.JWTConstant.*;

/**
 * @ClassName JwtUtil
 * @description: JWT工具类，提供生成和解析JWT token的方法
 * @date 2024年07月24日
 * @version: 1.0
 */
public class JwtUtil {



    /**
     * 生成JWT token
     * @param claims 业务数据
     * @return 生成的JWT token
     */
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim(CLAIMS, claims) // 添加业务数据
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
                .sign(Algorithm.HMAC256(KEY)); // 使用HMAC256算法签名
    }

    /**
     * 解析JWT token并返回业务数据
     * @param token JWT token
     * @return 业务数据
     * @throws JWTVerificationException 如果token无效或过期
     */
    public static Map<String, Object> parseToken(String token) throws JWTVerificationException {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(KEY)).build(); // 创建JWT验证器
            return verifier.verify(token) // 验证token
                    .getClaim(CLAIMS) // 获取业务数据
                    .asMap(); // 转换为Map
        } catch (JWTVerificationException e) {
            // 捕获JWT验证异常并抛出，以便调用者处理
            throw new JWTVerificationException("令牌验证失败： " + e.getMessage());
        }
    }
}
