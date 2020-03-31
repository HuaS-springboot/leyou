package com.vsj.material.mapper;

import com.sun.org.apache.regexp.internal.RE;
import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.MaterialMemberPackage;
import com.vsj.material.model.MaterialUserLevel;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.MaterialMemberPackageResponse;
import com.vsj.material.model.response.MaterialUserLevelResponse;
import com.vsj.material.model.response.VsjMaterialUserResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjMaterialUserMapper
 * @Description 会员mapper
 * @Date 2019/8/14 10:05
 * @Created by wangzx
 */
@Mapper
public interface VsjMaterialUserMapper {

    @SelectProvider(type = VsjMaterialUserMapper.VsjMaterialUserProvider.class,method = "getUserList")
    List<VsjMaterialUserResponse> getUserList(QueryStat queryStat);
    @UpdateProvider(type = VsjMaterialUserMapper.VsjMaterialUserProvider.class, method = "updateMemberPackage")
    int updateMemberPackage(MaterialMemberPackage materialMemberPackage);
    @SelectProvider(type = VsjMaterialUserMapper.VsjMaterialUserProvider.class,method = "getUserLevelList")
    List<MaterialUserLevelResponse> getUserLevelList(QueryStat queryStat);
    @UpdateProvider(type = VsjMaterialUserMapper.VsjMaterialUserProvider.class, method = "updateUserLevel")
    int updateUserLevel(MaterialUserLevel materialUserLevel);
    @InsertProvider(type = VsjMaterialUserMapper.VsjMaterialUserProvider.class, method = "addUserLevel")
    int addUserLevel(MaterialUserLevel materialUserLevel);
    @SelectProvider(type = VsjMaterialUserMapper.VsjMaterialUserProvider.class,method = "getMaterialPackage")
    List<MaterialMemberPackageResponse> getMaterialPackage(QueryStat queryStat);
    @DeleteProvider(type = VsjMaterialUserMapper.VsjMaterialUserProvider.class, method = "deleteUserLevel")
    int deleteUserLevel(QueryStat querySet);

    class VsjMaterialUserProvider{

        public String getUserList(QueryStat queryStat){
            String sql = new SQL(){{
                SELECT("r.id,r.nick_name,r.create_time,r.tel_phone,r.head_url,m.join_time,m.level_id,l.level_name");
                SELECT("m.expired_time,r.platform_code");
                FROM("vsj_material_register r");
                LEFT_OUTER_JOIN("vsj_material_member m ON r.id = m.reg_id");
                LEFT_OUTER_JOIN("vsj_material_member_level l ON m.level_id = l.id");
                String where = whereBuilder(queryStat);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
                ORDER_BY("r.id DESC");
            }}.toString();
            return sql;
        }

        private String whereBuilder(QueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("r.platform_code = #{platformCode}");
            if(StringUtil.isNoEmptyStr(queryStat.getNickName())){
                list.add("r.nick_name like concat('%',#{nickName},'%')");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getTelPhone())){
                list.add("r.tel_phone like concat('%',#{telPhone},'%') ");
            }
            if(queryStat.getLevelId() != null){
                list.add("m.level_id = #{levelId} ");
            }
            return String.join(" and ", list);
        }
        public String updateMemberPackage(MaterialMemberPackage materialMemberPackage) {
            String sql = new SQL() {
                {
                    UPDATE("vsj_material_package");
                    if (materialMemberPackage.getDay()!=null) {
                        SET("day = #{day}");
                    }
                    if (materialMemberPackage.getMoney()!=null) {
                        SET("money = #{money}");
                    }
                    if (materialMemberPackage.getLevelId()!=null){
                        SET("level_id = #{levelId}");
                    }
                    if (materialMemberPackage.getStatus()!=null){
                        SET("status = #{status}");
                    }
                    WHERE("id = #{id} and platform_code = #{platformCode}");
                }
            }.toString();
            return sql;
        }
        public String getUserLevelList(QueryStat queryStat){
            String sql = new SQL(){{
                SELECT("id,level_name,sort,is_default,platform_code");
                FROM("vsj_material_member_level");
                ORDER_BY("sort DESC");
            }}.toString();
            return sql;
        }
        public String updateUserLevel(MaterialUserLevel materialUserLevel) {
            String sql = new SQL() {
                {
                    UPDATE("vsj_material_member_level");
                    if (StringUtil.isNoEmptyStr(materialUserLevel.getLevelName())) {
                        SET("level_name = #{levelName}");
                    }
                    if (materialUserLevel.getSort()!=null) {
                        SET("sort = #{sort}");
                    }
                    if (materialUserLevel.getIsDefault()!=null){
                        SET("is_default = #{isDefault}");
                    }
                    WHERE("id = #{id} and platform_code = #{platformCode}");
                }
            }.toString();
            return sql;
        }
        public String addUserLevel(MaterialUserLevel materialUserLevel){
            String sql = new SQL(){{
                INSERT_INTO("vsj_material_member_level");
                if(StringUtil.isNoEmptyStr(materialUserLevel.getLevelName())){
                    VALUES("level_name","#{levelName}");
                }
                if(materialUserLevel.getSort()!=null){
                    VALUES("sort","#{sort}");
                }
                if(materialUserLevel.getIsDefault()!=null){
                    VALUES("is_default","#{isDefault}");
                }
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;

        }
        public String getMaterialPackage(QueryStat queryStat){
            String sql = new SQL(){{
                SELECT("id,status,level_id,day,money");
                FROM("vsj_material_package");
            }}.toString();
            return sql;
        }
        public String deleteUserLevel(QueryStat querySet) {
            String sql = new SQL() {
                {
                    DELETE_FROM("vsj_material_member_level");
                    WHERE("FIND_IN_SET(id,#{ids}) and platform_code = #{platformCode}");
                }
            }.toString();
            return sql;
        }
    }
}
