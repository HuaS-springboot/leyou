package com.vsj.mapper;

import com.vsj.model.response.OrderResponse;
import org.apache.ibatis.annotations.Update;

/**
 * @Classname RegisterAddressMapper
 * @Description TODO
 * @Date 2019/8/20 11:00
 * @Created by zy
 */
public interface RegisterAddressMapper {

    @Update("UPDATE vsj_register_address SET realname = #{realName}," +
            "telphone = #{telPhone},country =#{country},province =#{province}" +
            "city = #{city},area =#{area}, street = #{street},zip = #{zip} WHERE address_id =#{addressId}")
    int updateAddress(OrderResponse orderResponse);
}
