package com.wszqj.controller;

import com.wszqj.mapper.DeliveryAddressMapper;
import com.wszqj.mapper.UserMapper;
import com.wszqj.pojo.dto.DeliveryAddressDTO;
import com.wszqj.pojo.dto.UserDTO;
import com.wszqj.pojo.dto.UserLoginDTO;
import com.wszqj.pojo.entry.DeliveryAddress;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.UserDetailVO;
import com.wszqj.pojo.vo.UserVO;
import com.wszqj.service.IUserService;
import com.wszqj.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.wszqj.constant.BaseConstant.ID;
import static com.wszqj.constant.BaseConstant.NICKNAME;
import static com.wszqj.constant.JWTConstant.TOKEN;

/**
 * @ClassName UserController
 * @description: TODO
 * @date 2024年07月28日
 * @author: wszqj
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "用户相关接口")
public class UserController {
    private final IUserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final UserMapper userMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;

    /**
     * 模拟用户手机号登录
     *
     * @param userLoginDTO
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.UserVO>
     **/
    @Operation(summary = "手机号登录")
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        log.info("手机号登录：{}", userLoginDTO.getPhone());
        UserVO userVO = userMapper.selectByPhone(userLoginDTO.getPhone());
        if (userVO == null) {
            return Result.error("用户未注册");
        }
        // 整理用户信息
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID, userVO.getId());
        claims.put(NICKNAME, userVO.getNickname());
        // 生成token
        String token = JwtUtil.genToken(claims);
        // 将token存入redis
        stringRedisTemplate.opsForValue().set(TOKEN, token, 1, TimeUnit.HOURS);
        // 封装 userVO
        userVO.setMobile(userLoginDTO.getPhone());
        userVO.setToken(token);
        return Result.success(userVO, "登录成功");
    }

    /**
     * 获取用户详细信息
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.UserDetailVO>
     **/
    @Operation(summary = "获取用户详细信息")
    @GetMapping("/profile")
    public Result<UserDetailVO> getUserDetailVO() {
        log.info("获取用户详细信息");
        return userService.getUserDetailVO();
    }

    /**
     * 修改用户信息
     *
     * @param userDTO
     * @return com.wszqj.pojo.result.Result
     **/
    @Operation(summary = "修改用户信息")
    @PutMapping("/update")
    public Result<Void> updateUserInfo(@RequestBody UserDTO userDTO) {
        log.info("修改用户信息：{}", userDTO);
        return userService.updateUser(userDTO);
    }

    /**
     * 获取用户数收货地址列表
     *
     * @return com.wszqj.pojo.result.Result<java.util.List < com.wszqj.pojo.entry.DeliveryAddress>>
     **/
    @Operation(summary = "获取用户数收货地址列表")
    @GetMapping("/address")
    public Result<List<DeliveryAddress>> getDeliveryAddressList() {
        log.info("获取用户数收货地址列表");
        return userService.getDeliveryAddressList();
    }

    /**
     * 新增用户收货地址
     *
     * @param deliveryAddressDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "新增用户收货地址")
    @PostMapping("/addDeliveryAddress")
    public Result<Void> addDeliveryAddress(@RequestBody @Validated DeliveryAddressDTO deliveryAddressDTO) {
        log.info("新增用户收货地址：{}", deliveryAddressDTO);
        return userService.addDeliveryAddress(deliveryAddressDTO);
    }


    /**
     * 更新用户地址信息
     *
     * @param deliveryAddressDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "更新用户地址信息")
    @PutMapping("/updateDeliveryAddress")
    public Result<Void> updateDeliveryAddress(@RequestBody @Validated DeliveryAddressDTO deliveryAddressDTO) {
        log.info("更新地址信息：{}", deliveryAddressDTO);
        return userService.updateDeliveryAddress(deliveryAddressDTO);
    }

    /**
     * 根据ID查询用户地址
     *
     * @param id
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.entry.DeliveryAddress>
     **/
    @Operation(summary = "根据ID查询用户地址")
    @GetMapping("/deliveryAddress")
    public Result<DeliveryAddress> getDeliveryAddress(@RequestParam("id") @Schema(description = "地址Id") String id) {
        log.info("根据ID查询地址：{}", id);
        return Result.success(deliveryAddressMapper.selectById(id));
    }

    /**
     *  根据id删除地址信息
     * @param id
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Operation(summary = "根据id删除地址信息")
    @DeleteMapping("/deliveryAddress")
    public Result<Void> deleteDeliveryAddress(@RequestParam("id") @NotEmpty @Schema(description = "地址Id") String id) {
        log.info("根据id删除地址信息：{}",id);
        deliveryAddressMapper.deleteById(id);
        return Result.success("删除成功");
    }
}
