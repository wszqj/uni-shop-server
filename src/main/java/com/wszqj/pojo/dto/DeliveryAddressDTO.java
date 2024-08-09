package com.wszqj.pojo.dto;

import com.wszqj.utils.RegexPatterns;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @ClassName DeliveryAddressDTO
 * @description: TODO
 * @date 2024年07月29日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@Schema(name = "DeliveryAddressDTO",description = "用户收货地址")
public class DeliveryAddressDTO {
    // 主键ID
    @Schema(name = "id",description = "主键ID")
    private Integer id;

    // 用户ID
    @Schema(name = "userId",description = "用户ID")
    private Integer userId;

    // 收货人
    @Schema(name = "consignee",description = "收货人")
    private String consignee;

    // 手机号码
    @Schema(name = "phone",description = "手机号码")
    @Pattern(regexp = RegexPatterns.PHONE_REGEX)
    private String phone;

    // 省市区
    @Schema(name = "fullLocation",description = "省市区")
    private String fullLocation;

    // 详细地址
    @Schema(name = "address",description = "详细地址")
    private String address;

    // 是否默认 (0: 否, 1: 是)
    @Schema(name = "status",description = "是否默认 (0: 否, 1: 是)")
    private Byte status;
}
