package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjMemberLevel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import java.util.List;


/**
 * @Author: HuaS
 * @Date :2019/7/24 11:25
 * @Describe:
 */
@Mapper
public interface MemberLevelMapper {

    public static final String tableName = " vsj_member_level ";

    public static final String baseColumn = " id,level_name,sort,is_default,platform_code ";


    @InsertProvider(type = MemberProvider.class, method = "saveMemberLevel")
    int saveMemberLevel(VsjMemberLevel vsjMemberLevel);


    @SelectProvider(type = MemberProvider.class, method = "findAllLevel")
    List<VsjMemberLevel> findAllLevel(Integer platformCode);


    @DeleteProvider(type = MemberProvider.class, method = "deleteLevelById")
    int deleteLevelById(@Param("levelId") Integer levelId, @Param("platformCode") Integer platformCode);

    @UpdateProvider(type = MemberProvider.class, method = "updateLevel")
    int updateLevel(VsjMemberLevel vsjMemberLevel);

    @SelectProvider(type = MemberProvider.class, method = "getLevelBySort")
    VsjMemberLevel getLevelBySort(@Param("sort") int sort, @Param("platformCode") Integer platformCode);

    @UpdateProvider(type = MemberProvider.class, method = "editDefaultLevel")
    int editDefaultLevel(@Param("id") int id, @Param("platformCode") int platformCode, @Param("isDefault") int isDefault);


    class MemberProvider {

        public String updateLevel(VsjMemberLevel vsjMemberLevel) {
            String sql = new SQL() {{
                UPDATE("vsj_member_level");
                if (StringUtil.isNoEmptyStr(vsjMemberLevel.getLevelName())) {
                    SET("level_name = #{levelName}");
                }
                if (vsjMemberLevel.getSort() != null) {
                    SET("sort = #{sort}");
                }
                if (vsjMemberLevel.getIsDefault() != null) {
                    SET("is_default = #{isDefault}");
                }
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }


        public String deleteLevelById(@Param("levelId") Integer levelId, @Param("platformCode") Integer platformCode) {
            String sql = new SQL() {{
                DELETE_FROM("vsj_member_level");
                WHERE("id = #{levelId} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String saveMemberLevel(VsjMemberLevel vsjMemberLevel) {
            String sql = new SQL() {{
                INSERT_INTO("vsj_member_level");
                VALUES("level_name", "#{levelName}");
                VALUES("sort", "#{sort}");
                if (vsjMemberLevel.getIsDefault() != null) {
                    VALUES("is_default", "#{isDefault}");
                }
                VALUES("platform_code", "#{platformCode}");
            }}.toString();
            return sql;
        }

        public String findAllLevel(Integer platformCode) {
            String sql = new SQL() {{
                SELECT(baseColumn);
                FROM(tableName);
                WHERE(" platform_code = #{platformCode}");
                ORDER_BY("sort desc");
            }}.toString();
            return sql;
        }

        public String getLevelBySort(@Param("sort") Integer sort, @Param("platformCode") Integer platformCode) {
            String sql = new SQL() {{
                SELECT(baseColumn);
                FROM(tableName);
                WHERE("sort = #{sort} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String editDefaultLevel(@Param("id") int id, @Param("platformCode") int platformCode, @Param("isDefault") int isDefault) {
            String sql = new SQL() {{
                UPDATE("vsj_member_level");
                SET("is_default = #{isDefault}");
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

    }
}
