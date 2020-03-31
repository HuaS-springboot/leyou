package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjSysDistTemplate;

import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @Classname DistTemplateMapper
 * @Description TODO
 * @Date 2019/7/26 9:44
 * @Created by wangzx
 */
@Mapper
public interface DistTemplateMapper {

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = DistTemplateMapper.DistTemplateProvider.class,method = "insertDistTemplate")
    int insertDistTemplate(VsjSysDistTemplate vsjSysDistTemplate);

    @Select("select id,`status`,configuration,create_time from vsj_sys_dist_template where platform_code = #{platformCode} order by id desc")
    List<VsjSysDistTemplate> getDistTemplateList(BaseQueryStat queryStat);

    @Select("select id,`status`,configuration,create_time from vsj_sys_dist_template where id = #{id} and platform_code = #{platformCode}")
    VsjSysDistTemplate getDistTemplateDetail(BaseQueryStat queryStat);

    @UpdateProvider(type = DistTemplateMapper.DistTemplateProvider.class,method = "updateDistTemplate")
    int updateDistTemplate(VsjSysDistTemplate vsjSysDistTemplate);

    @Delete("delete from vsj_sys_dist_template where id = #{id} and platform_code = #{platformCode}")
    int deleteDistTemplate(BaseQueryStat queryStat);

    @Select("select count(1) from vsj_sys_dist_template where platform_code = #{platformCode}")
    int getDistCount(Integer platformCode);


    class DistTemplateProvider{

        public String insertDistTemplate(VsjSysDistTemplate vsjSysDistTemplate){
            String sql = new SQL(){{
                INSERT_INTO("vsj_sys_dist_template");
                if(vsjSysDistTemplate.getStatus() != null){
                    VALUES("`status`","#{status}");
                }
                if(StringUtil.isNoEmptyStr(vsjSysDistTemplate.getConfiguration())){
                    VALUES("configuration","#{configuration}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String updateDistTemplate(VsjSysDistTemplate vsjSysDistTemplate){
            String sql = new SQL(){{
                UPDATE("vsj_sys_dist_template");
                if(vsjSysDistTemplate.getStatus() != null){
                    SET("`status` = #{status}");
                }
                if(StringUtil.isNoEmptyStr(vsjSysDistTemplate.getConfiguration())){
                    SET("`configuration` = #{configuration}");
                }
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
