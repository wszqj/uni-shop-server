package com.wszqj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @ClassName UserDetailVO
 * @description: 用户详情信息类
 * @date 2024年07月28日
 * @author: wszqj
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDetailVO {

    /** 用户ID */
    private int id;

    /** 头像 */
    private String avatar;

    /** 账户名 */
    private String account;

    /** 昵称 */
    private String nickname;

    /** 性别 */
    private String gender;

    /** 生日 */
    private LocalDate birthday;

    /** 省市区 */
    private String fullLocation;
}
