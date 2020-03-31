package com.vsj.material.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.MaterialConversion;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.MaterialConversionResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSourceExtensionsKt;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MaterialConversionMapper
 * @Description 兑换码mapper
 * @Date 2019/8/16 10:50
 * @Created by wangzx
 */
@Mapper
public interface MaterialConversionMapper {

    @InsertProvider(type = MaterialConversionMapper.MaterialConversionProvider.class, method = "insertMaterialConversion")
    int insertMaterialConversion(MaterialConversion materialConversion);

    @SelectProvider(type = MaterialConversionMapper.MaterialConversionProvider.class, method = "getMaterialConversionList")
    List<MaterialConversionResponse> getMaterialConversionList(QueryStat queryStat);

    @SelectProvider(type = MaterialConversionMapper.MaterialConversionProvider.class,method = "getConversionByCode")
    MaterialConversion getConversionByCode(QueryStat queryStat);

    @Delete("delete from vsj_material_conversion where FIND_IN_SET(id, #{ids}) and platform_code = #{platformCode}")
    int deleteMaterialConversion(QueryStat queryStat);


    class MaterialConversionProvider {

        public String insertMaterialConversion(MaterialConversion materialConversion) {
            String sql = new SQL() {{
                INSERT_INTO("vsj_material_conversion");
                if(StringUtil.isNoEmptyStr(materialConversion.getCode())){
                    VALUES("code","#{code}");
                }
                if(materialConversion.getLevelId() != null){
                    VALUES("level_id","#{levelId}");
                }
                if(StringUtil.isNoEmptyStr(materialConversion.getEffectiveTime())){
                    VALUES("effective_time","#{effectiveTime}");
                }
                if(StringUtil.isNoEmptyStr(materialConversion.getInvalidTime())){
                    VALUES("invalid_time","#{invalidTime}");
                }
                if(materialConversion.getLevelDay() != null){
                    VALUES("level_day","#{levelDay}");
                }
                if(materialConversion.getStatus() != null){
                    VALUES("status","#{status}");
                }
                VALUES("platform_code","#{platformCode}");
                VALUES("create_time","NOW()");
            }}.toString();
            return sql;
        }

        public String getMaterialConversionList(QueryStat queryStat){
            String sql = new SQL(){{
                SELECT("c.id,c.`code`,c.level_id,c.level_day,c.effective_time,c.invalid_time,c.`status`");
                SELECT("l.level_name,r.nick_name,c.use_time,r.tel_phone");
                FROM("vsj_material_conversion c");
                LEFT_OUTER_JOIN("vsj_material_member_level l ON l.id = c.level_id");
                LEFT_OUTER_JOIN("vsj_material_member m ON c.use_member_id = m.id");
                LEFT_OUTER_JOIN("vsj_material_register r ON r.id = m.reg_id");
                String where = whereBuild(queryStat);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
                ORDER_BY("c.id DESC");
            }}.toString();
            return sql;
        }

        private String whereBuild(QueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("c.platform_code = #{platformCode}");
            if(StringUtil.isNoEmptyStr(queryStat.getCode())){
                list.add("c.`code` like concat('%',#{code},'%')");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getNickName())){
                list.add("r.nick_name like concat('%',#{nickName},'%')");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getTelPhone())){
                list.add("r.tel_phone like concat('%',#{telPhone},'%')");
            }
            if(queryStat.getStatus() != null){
                list.add("c.`status` = #{status}");
            }
            if(queryStat.getLevelId() != null){
                list.add("c.level_id = #{levelId}");
            }
            return String.join(" and ", list);
        }

        public String getConversionByCode(QueryStat queryStat){
            String sql=new SQL(){{
                SELECT("*");
                FROM("vsj_material_conversion");
                WHERE("code = #{code} and platform_code = #{platformCode}");
            }}.toString();
            return sql;

        }

    }
}
