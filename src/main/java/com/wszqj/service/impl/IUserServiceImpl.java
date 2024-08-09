package com.wszqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wszqj.mapper.DeliveryAddressMapper;
import com.wszqj.mapper.UserMapper;
import com.wszqj.pojo.dto.DeliveryAddressDTO;
import com.wszqj.pojo.dto.UserDTO;
import com.wszqj.pojo.entry.DeliveryAddress;
import com.wszqj.pojo.entry.User;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.UserDetailVO;
import com.wszqj.service.IDeliveryAddressService;
import com.wszqj.service.IUserService;
import com.wszqj.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName IUserServiceImpl
 * @description: TODO
 * @date 2024年07月28日
 * @author: wszqj
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final UserMapper userMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final IDeliveryAddressService deliveryAddressService;

    /**
     * 获取用户详细信息
     *
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.UserDetailVO>
     **/
    @Override
    public Result<UserDetailVO> getUserDetailVO() {
        // 获取当前操作用户id
        Integer userId = ThreadLocalUtil.getUserId();
        // 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("不存在该用户");
        }// 封装返回数据
        return Result.success(UserDetailVO.builder()
                .id(userId)
                .account(user.getAccount())
                .avatar(user.getAvatar())
                .birthday(user.getBirthday())
                .gender(user.getGender() == 0 ? "女性" : "男性")
                .nickname(user.getNickname())
                .fullLocation(user.getFullLocation())
                .build());
    }

    /**
     * 修改用户信息
     *
     * @param userDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Override
    public Result<Void> updateUser(UserDTO userDTO) {
        // 获取当前操作用户id
        Integer userId = ThreadLocalUtil.getUserId();
        // 获取性别映射值
        Byte gender = userDTO.getGender().equals("女") ? (byte) 1 : (byte) 0;
        // 更新数据
        userMapper.updateById(User.builder()
                .id(userId)
                .fullLocation(userDTO.getFullLocation())
                .birthday(userDTO.getBirthday())
                .gender(gender)
                .nickname(userDTO.getNickname())
                .build());
        return Result.success("更新成功");
    }

    /**
     * 获取用户数收货地址列表
     *
     * @return com.wszqj.pojo.result.Result<java.util.List < com.wszqj.pojo.entry.DeliveryAddress>>
     **/
    @Override
    public Result<List<DeliveryAddress>> getDeliveryAddressList() {
        // 获取当前操作用户id
        Integer userId = ThreadLocalUtil.getUserId();
        // 获取地址列表
        List<DeliveryAddress> deliveryAddressList = userMapper.getDeliveryAddressListByUserId(userId);
        return Result.success(deliveryAddressList);
    }

    /**
     * 新增用户收货地址
     *
     * @param deliveryAddressDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Override
    @Transactional
    public Result<Void> addDeliveryAddress(DeliveryAddressDTO deliveryAddressDTO) {
        // 检查状态
        checkedStatus(deliveryAddressDTO);
        // 获取当前操作用户id
        Integer userId = ThreadLocalUtil.getUserId();
        // 构造地址并保存
        deliveryAddressMapper.insert(DeliveryAddress.builder()
                .userId(userId)
                .address(deliveryAddressDTO.getAddress())
                .phone(deliveryAddressDTO.getPhone())
                .status(deliveryAddressDTO.getStatus())
                .consignee(deliveryAddressDTO.getConsignee())
                .fullLocation(deliveryAddressDTO.getFullLocation())
                .build());
        return Result.success("添加成功");
    }

    /**
     * deliveryAddressDTO
     *
     * @param deliveryAddressDTO 收货地址数据传输对象
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    @Override
    @Transactional
    public Result<Void> updateDeliveryAddress(DeliveryAddressDTO deliveryAddressDTO) {
        // 检查状态
        checkedStatus(deliveryAddressDTO);
        // 更新地址信息
        deliveryAddressMapper.updateById(
                DeliveryAddress.builder()
                        .id(deliveryAddressDTO.getId())
                        .address(deliveryAddressDTO.getAddress())
                        .phone(deliveryAddressDTO.getPhone())
                        .status(deliveryAddressDTO.getStatus())
                        .consignee(deliveryAddressDTO.getConsignee())
                        .fullLocation(deliveryAddressDTO.getFullLocation())
                        .build());
        return Result.success("更新成功");
    }

    /**
     *  检查是否被设置为默认地址，并将其他所有地址状态设为非默认
     * @param deliveryAddressDTO 收货地址数据传输对象
     * @return void
     **/
    public void checkedStatus(DeliveryAddressDTO deliveryAddressDTO) {
        if (deliveryAddressDTO.getStatus() == 0){
            // 不需要更新其他数据
            return;
        }
        // 需要更新其他数据
        LambdaUpdateWrapper<DeliveryAddress> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DeliveryAddress::getStatus, 0);
        deliveryAddressMapper.update(null, updateWrapper);
    }
}
