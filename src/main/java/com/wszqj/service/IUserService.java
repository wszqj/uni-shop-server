package com.wszqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wszqj.pojo.dto.DeliveryAddressDTO;
import com.wszqj.pojo.dto.UserDTO;
import com.wszqj.pojo.entry.DeliveryAddress;
import com.wszqj.pojo.entry.User;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.UserDetailVO;

import java.util.List;

/**
 * @ClassName IUserService
 * @description: TODO
 * @date 2024年07月28日
 * @author: wszqj
 * @version: 1.0
 */
public interface IUserService extends IService<User> {
    /**
     *  获取用户详细信息
     * @return com.wszqj.pojo.result.Result<com.wszqj.pojo.vo.UserDetailVO>
     **/
    Result<UserDetailVO> getUserDetailVO();

    /**
     *  修改用户信息
     * @param userDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> updateUser(UserDTO userDTO);

    /**
     *  获取用户数收货地址列表
     * @return com.wszqj.pojo.result.Result<java.util.List<com.wszqj.pojo.entry.DeliveryAddress>>
     **/
    Result<List<DeliveryAddress>> getDeliveryAddressList();

    /**
     *  新增用户收货地址
     * @param deliveryAddressDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> addDeliveryAddress(DeliveryAddressDTO deliveryAddressDTO);

    /**
     *  更新用户地址信息
     * @param deliveryAddressDTO
     * @return com.wszqj.pojo.result.Result<java.lang.Void>
     **/
    Result<Void> updateDeliveryAddress(DeliveryAddressDTO deliveryAddressDTO);
}
