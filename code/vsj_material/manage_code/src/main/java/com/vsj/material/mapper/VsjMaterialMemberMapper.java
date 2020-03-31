package com.vsj.material.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.VsjMaterialMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Classname VsjMaterialMemberMapper
 * @Description 会员相关mapper
 * @Date 2019/8/14 16:54
 * @Created by wangzx
 */
@Mapper
public interface VsjMaterialMemberMapper {

    @UpdateProvider(type = VsjMaterialMemberMapper.VsjMaterialMemberProvider.class,method = "updateMaterialMember")
    int updateMaterialMember(VsjMaterialMember vsjMaterialMember);

    class VsjMaterialMemberProvider{

        public String updateMaterialMember(VsjMaterialMember vsjMaterialMember){
            String sql = new SQL(){{
                UPDATE("vsj_material_member");
                if(StringUtil.isNoEmptyStr(vsjMaterialMember.getJoinTime())){
                    SET("join_time = #{joinTime}");
                }
                if(vsjMaterialMember.getLevelId() != null){
                    SET("level_id = #{levelId}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialMember.getExpiredTime())){
                    SET("expired_time = #{expiredTime}");
                }
                WHERE("reg_id = #{regId} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
