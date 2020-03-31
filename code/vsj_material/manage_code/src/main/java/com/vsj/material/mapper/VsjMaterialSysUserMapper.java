package com.vsj.material.mapper;

import com.vsj.material.model.VsjMaterialSysUser;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.UserLoginModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Classname VsjMaterialSysUserMapper
 * @Description 系统用户mapper
 * @Date 2019/8/14 14:00
 * @Created by sxm
 */
@Mapper
public interface VsjMaterialSysUserMapper {

    @SelectProvider(type = VsjMaterialSysUserMapper.VsjMaterialSysUserProvider.class,method = "selectSysUser")
    UserLoginModel selectSysUser(QueryStat querySet);

    class VsjMaterialSysUserProvider{

        public String selectSysUser(QueryStat querySet){
            String sql=new SQL(){{
                SELECT("id,user_name,pswd,nick_name,sex");
                FROM("vsj_material_sys_user");
                WHERE("user_name = #{userName} and platform_code = #{platformCode} and pswd = #{pswd}");

            }}.toString();
            return sql;
        }

    }

}
