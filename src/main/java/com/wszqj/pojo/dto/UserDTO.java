package com.wszqj.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @ClassName UserDTO
 * @description: TODO
 * @date 2024年07月29日
 * @author: wszqj
 * @version: 1.0
 */
@Data
@Schema(name = "UserDTO",description = "接收更新用户数据")
public class UserDTO {
    @Schema(name = "gender",description = "性别")
    private String gender;
    @Schema(name = "fullLocation",description = "省市区")
    private String fullLocation;
    @Schema(name = "birthday",description = "生日")
    private LocalDate birthday;
    @Schema(name = "nickname",description = "昵称")
    private String nickname;
}
