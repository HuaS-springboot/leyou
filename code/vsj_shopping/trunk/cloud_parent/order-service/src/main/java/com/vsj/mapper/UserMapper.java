package com.vsj.mapper;

import com.vsj.model.User;
import com.vsj.model.VsjSysAreas;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value ="UserMapper")
public interface UserMapper {
    /*
    * 查询用户列表
    * */
    @Select("SELECT * FROM vsj_register")
    List<User> findAll();
    /*
    * 根据ID查询用户信息
    * */
    @Select("SELECT * FROM vsj_register WHERE id=#{id}")
    User get(@Param("id") int id);

    @SelectProvider(type = UserMapper.UserProvider.class,method = "getSysAreas")
    List<VsjSysAreas> getSysAreas();

    class UserProvider{
        public String getSysAreas(){
            String sql = new SQL(){{
                SELECT("id,`code`,parent_code,`name`,province,city,district,full_name,grade");
                FROM("vsj_sys_areas");
            }}.toString();
            return sql;
        }
    }

}
