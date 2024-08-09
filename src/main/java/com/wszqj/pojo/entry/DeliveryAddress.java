package com.wszqj.pojo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户收货地址表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("delivery_address")
public class DeliveryAddress {

    @TableId(type = IdType.AUTO)
    private Integer id;  // 主键ID

    private Integer userId;  // 用户ID

    private String consignee;  // 收货人

    private String phone;  // 手机号码

    private String fullLocation;  // 省市区

    private String address;  // 详细地址

    private Byte status;  // 是否默认 (0: 否, 1: 是)
}
