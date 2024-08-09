package com.wszqj.constant;

/**
 * @ClassName JWTConstant
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
public class JWTConstant {
    public static final String AUTHORIZATION_HEADER = "Authorization"; // 常量，避免使用魔法字符串
    public static final String KEY = "wszqj"; // 签名密钥
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 12; // 过期时间，12小时
    public static final String CLAIMS = "claims"; // jwt数据键
    public static final String TOKEN = "token"; // jwt数据键
}
