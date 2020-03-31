package com.vsj.material.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.VsjMaterialCategory;
import com.vsj.material.model.VsjMaterialSysConfig;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Classname VsjMaterialSysConfigMapper
 * @Description 系统配置mapper
 * @Date 2019/8/13 11:57
 * @Created by wangzx
 */
@Mapper
public interface VsjMaterialSysConfigMapper {

    @Select("SELECT id,`key`,`value`,remark,platform_code from vsj_material_sys_config where `key` = #{key} and " +
            "platform_code = #{platformCode}")
    VsjMaterialSysConfig selectByKey(@Param("key") String key, @Param("platformCode") Integer platformCode);

    @UpdateProvider(type = VsjMaterialSysConfigMapper.VsjMaterialSysConfigProvider.class,method = "updateSysConfig")
    int updateSysConfig(VsjMaterialSysConfig vsjMaterialSysConfig);

    @Select("select id,cate_name,parent_id,sort,status,create_time,platform_code from vsj_material_category " +
            "where platform_code = #{platformCode}")
    List<VsjMaterialCategory> getCategoryList(Integer platformCode);

    class VsjMaterialSysConfigProvider{
        public String updateSysConfig(VsjMaterialSysConfig vsjMaterialSysConfig){
            String sql = new SQL(){{
                UPDATE("vsj_material_sys_config");
                if(StringUtil.isNoEmptyStr(vsjMaterialSysConfig.getValue())){
                    SET("`value` = #{value}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialSysConfig.getRemark())){
                    SET("remark = #{remark}");
                }
                WHERE("`key` = #{key} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
