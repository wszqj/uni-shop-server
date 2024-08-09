package com.wszqj.pojo.dto;

import com.wszqj.utils.RegexPatterns;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @ClassName UserDTO
 * @description: TODO
 * @date 2024年07月28日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@Schema(name = "UserLoginDTO",description = "用户登录对象")
public class UserLoginDTO {
    @Pattern(regexp = RegexPatterns.PHONE_REGEX)
    @Schema(name = "phone",description = "用户手机号")
    private String phone;
}
