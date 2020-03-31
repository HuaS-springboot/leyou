package com.vsj.material.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.VsjMaterialRegister;
import com.vsj.material.model.request.QueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjMaterialRegisterMapper
 * @Description 注册相关mapper
 * @Date 2019/8/14 16:36
 * @Created by wangzx
 */
@Mapper
public interface VsjMaterialRegisterMapper {

    @UpdateProvider(type = VsjMaterialRegisterMapper.VsjMaterialRegisterProvider.class,method = "updateRegisterById")
    int updateRegisterById(VsjMaterialRegister vsjMaterialRegister);

    @Options(useGeneratedKeys = true,keyProperty = "id ",keyColumn = "id")
    @InsertProvider(type = VsjMaterialRegisterMapper.VsjMaterialRegisterProvider.class,method ="insertRegister")
    int insertRegister(VsjMaterialRegister vsjMaterialRegister);

    @SelectProvider(type = VsjMaterialRegisterMapper.VsjMaterialRegisterProvider.class,method = "selectRegister")
    List<VsjMaterialRegister> selectRegister(QueryStat queryStat);

    class VsjMaterialRegisterProvider{

        public String updateRegisterById(VsjMaterialRegister vsjMaterialRegister){
            String sql = new SQL(){{
                UPDATE("vsj_material_register");
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getOpenId())){
                    SET("open_id = #{openId}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getNickName())){
                    SET("nick_name = #{nickName}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getHeadUrl())){
                    SET("head_url = #{headUrl}");
                }
                if(vsjMaterialRegister.getSex() != null){
                    SET("sex = #{sex}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getCountry())){
                    SET("country = #{country}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getProvince())){
                    SET("province = #{province}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getCity())){
                    SET("city = #{city}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getArea())){
                    SET("area = #{area}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getLanguage())){
                    SET("language = #{language}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getCreateTime())){
                    SET("create_time = #{createTime}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getTelPhone())){
                    SET("tel_phone = #{telPhone}");
                }
                if(vsjMaterialRegister.getType() != null){
                    SET("type = #{type}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getRemark())){
                    SET("remark = #{remark}");
                }
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }


        public String insertRegister(VsjMaterialRegister vsjMaterialRegister){
            String sql = new SQL(){{
                INSERT_INTO("vsj_material_register");
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getOpenId())){
                    VALUES("open_id","#{openId}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getNickName())){
                    VALUES("nick_name","#{nickName}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getHeadUrl())){
                    VALUES("head_url","#{headUrl}");
                }
                if(vsjMaterialRegister.getSex() != null){
                    VALUES("sex","#{sex}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getCountry())){
                    VALUES("country","#{country}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getProvince())){
                    VALUES("province","#{province}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getCity())){
                    VALUES("city","#{city}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getArea())){
                    VALUES("area","#{area}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getLanguage())){
                    VALUES("language","#{language}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getCreateTime())){
                    VALUES("create_time","#{createTime}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getTelPhone())){
                    VALUES("tel_phone"," #{telPhone}");
                }
                if(vsjMaterialRegister.getType() != null){
                    VALUES("type","#{type}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getRemark())){
                    VALUES("remark","#{remark}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterialRegister.getRemark())){
                    VALUES("id","#{id}");
                }
                    VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;

        }

        public String selectRegister(QueryStat querySet){
            String sql = new SQL(){{
                SELECT("*");
                FROM("vsj_material_register");
                String where = whereBuilder(querySet);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
            }}.toString();
            return sql;
        }

        private String whereBuilder(QueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("platform_code = #{platformCode}");
            if(queryStat.getId()!=null){
                list.add("id = #{id}");
            }
            return String.join(" and ",list);
        }
    }
}
