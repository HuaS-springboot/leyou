package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjMemberUpgradeRules;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjMemberUpgradeRulesMapper
 * @Description TODO
 * @Date 2019/8/5 15:30
 * @Created by wangzx
 */
@Mapper
public interface VsjMemberUpgradeRulesMapper {

    @SelectProvider(type = VsjMemberUpgradeRulesMapper.UpgradeRulesProvider.class,method = "getRulesByParentId")
    VsjMemberUpgradeRules getRulesByParentId(@Param("parentId") Integer parentId, @Param("platformCode") Integer platformCode,@Param("levelId") Integer levelId);

    @SelectProvider(type = VsjMemberUpgradeRulesMapper.UpgradeRulesProvider.class,method = "findMemberUpgradeRules")
    List<VsjMemberUpgradeRules> findMemberUpgradeRules(@Param("levelId")Integer levelId,@Param("platformCode")Integer platformCode);

    @InsertProvider(type = VsjMemberUpgradeRulesMapper.UpgradeRulesProvider.class,method = "insert")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insert(VsjMemberUpgradeRules vsjMemberUpgradeRules);

    @UpdateProvider(type = VsjMemberUpgradeRulesMapper.UpgradeRulesProvider.class,method = "updateByPrimaryKey")
    int updateByPrimaryKey(VsjMemberUpgradeRules vsjMemberUpgradeRules);

    @DeleteProvider(type = VsjMemberUpgradeRulesMapper.UpgradeRulesProvider.class,method = "delByPrimaryKey")
    int delByPrimaryKey(@Param("id")Integer id,@Param("platformCode")Integer platformCode);

     class  UpgradeRulesProvider{

         public String getRulesByParentId(Integer parentId,Integer platformCode,Integer levelId){
            String sql = new SQL(){{
                SELECT("id,type,parent_id,data,current_relation,parent_relation,level_id,platform_code");
                FROM("vsj_member_upgrade_rules");
                String where = whereBuilder(parentId, platformCode);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
            }}.toString();
            return sql;
         }

         private String whereBuilder(Integer parentId,Integer platformCode){
             List<String> list = new ArrayList<>();
                if(parentId == null){
                    list.add("parent_id = 0");
                }else {
                    list.add("parent_id = #{parentId}");
                }
                list.add("platform_code = #{platformCode}");
                list.add("level_id = #{levelId}");
             return String.join(" and ", list);
         }

         public String findMemberUpgradeRules(@Param("levelId")Integer levelId,@Param("platformCode")Integer platformCode){
             String sql = new SQL(){{
                 SELECT("id,type,parent_id,data,current_relation,parent_relation,level_id,platform_code");
                 FROM("vsj_member_upgrade_rules");
                 WHERE("level_id = #{levelId} and platform_code = #{platformCode}");
             }}.toString();
             return sql;
         }

         public String insert(VsjMemberUpgradeRules vsjMemberUpgradeRules){
             String sql = new SQL(){{
                INSERT_INTO("vsj_member_upgrade_rules");
                VALUES("type","#{type}");
                VALUES("parent_id","#{parentId}");
                VALUES("data","#{data}");
                VALUES("current_relation","#{currentRelation}");
                if(null != vsjMemberUpgradeRules.getParentRelation()){
                    VALUES("parent_relation","#{parentRelation}");
                }
                VALUES("level_id","#{levelId}");
                VALUES("platform_code","#{platformCode}");
             }}.toString();
             return sql;
         }

         public String updateByPrimaryKey(VsjMemberUpgradeRules vsjMemberUpgradeRules){
             String sql = new SQL(){{
                 UPDATE("vsj_member_upgrade_rules");
                 SET("type = #{type}");
                 SET("parent_id = #{parentId}");
                 SET("data = #{data}");
                 SET("current_relation = #{currentRelation}");
                 if(null != vsjMemberUpgradeRules.getParentRelation()){
                     SET("parent_relation = #{parentRelation}");
                 }
                 WHERE("id = #{id} and platform_code = #{platformCode}");
             }}.toString();
             return sql;
         }

         public String delByPrimaryKey(@Param("id")Integer id,@Param("platformCode")Integer platformCode){
             String sql = new SQL(){{
                 DELETE_FROM("vsj_member_upgrade_rules");
                 WHERE("id = #{id} and platform_code = #{platformCode}");
             }}.toString();
             return sql;
         }
    }
}
