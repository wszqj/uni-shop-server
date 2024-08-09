package com.wszqj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wszqj.pojo.dto.UserDTO;
import com.wszqj.pojo.entry.DeliveryAddress;
import com.wszqj.pojo.entry.User;
import com.wszqj.pojo.result.Result;
import com.wszqj.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName UserMapper
 * @description: TODO
 * @date 2024年07月28日
 * @author: wszqj
 * @version: 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select id, avatar, account, nickname from wszqj_get_money.users where phone = #{phone}")
    UserVO selectByPhone(@Param("phone") String phone);

    @Select("select id, user_id, consignee, phone, full_location, address, status from wszqj_get_money.delivery_address where user_id = #{userId}")
    List<DeliveryAddress> getDeliveryAddressListByUserId(@Param("userId") Integer userId);
}
