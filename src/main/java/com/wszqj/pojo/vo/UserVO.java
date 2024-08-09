package com.wszqj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserVO
 * @description: 小程序登录 登录用户信息
 * @date 2024年07月28日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    /** 用户ID */
    private Integer id;
    /** 头像  */
    private String avatar;
    /** 账户名  */
    private String account;
    /** 昵称 */
    private String nickname;
    /** 手机号 */
    private String mobile;
    /** 登录凭证 */
    private String token;
}
