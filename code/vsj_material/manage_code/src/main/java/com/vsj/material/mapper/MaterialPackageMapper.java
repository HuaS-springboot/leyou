package com.vsj.material.mapper;

import com.vsj.material.model.MaterialMemberPackage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Classname MaterialPackageMapper
 * @Description 套餐mapper
 * @Date 2019/8/20 17:30
 * @Created by wangzx
 */
@Mapper
public interface MaterialPackageMapper {

    @Select("select id,status,level_id,day,money,platform_code where id= #{packageId} and platform_code = #{platformCode}")
    MaterialMemberPackage getMaterialPackageById(@Param("packageId") Integer packageId, @Param("platformCode") Integer platformCode);
}
