package com.wszqj.pojo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName User
 * @description: TODO
 * @date 2024年07月24日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("users")
public class User {

    // 主键，自增
    @TableId(type = IdType.AUTO)//设置主键的自增策略为主键自增（mybatisPlus默认为雪花算法）
    private Integer id;

    // 用户头像，可为空
    private String avatar;

    // 昵称，可为空
    private String nickname;

    // 账户号，可为空
    private String account;

    // 手机号，可为空
    private String phone;

    // 性别，tinyint类型，可为空
    private Byte gender;

    // 生日，date类型，可为空
    private LocalDate birthday;

    // 详细地址，可为空
    private String address;

    // 省市区，可为空
    private String fullLocation;

    // 创建时间，可为空
    private LocalDateTime createdAt;
}
