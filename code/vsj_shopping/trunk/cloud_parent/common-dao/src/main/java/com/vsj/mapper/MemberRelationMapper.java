package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjMemberRelationSet;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/7/25 18:28
 * @Describe:
 */

@Mapper
public interface MemberRelationMapper {

    @InsertProvider(type = RelationMapper.class, method = "saveMemberRelation")
    int saveMemberRelation(VsjMemberRelationSet vsjSysRelationSet);

    @UpdateProvider(type = RelationMapper.class, method = "updateMemberRelation")
    int updateMemberRelation(VsjMemberRelationSet vsjSysRelationSet);

    @SelectProvider(type = RelationMapper.class, method = "getMemberRelation")
    VsjMemberRelationSet getMemberRelation(Integer platformCode);

    class RelationMapper {

        public String getMemberRelation(Integer platformCode) {
            String sql = new SQL() {{
                SELECT("r.id,r.relation_switch,r.need_require,r.bind_phone,r.expense_money");
                SELECT("r.expense_num,r.product_ids,r.product_num,r.offline_conditions");
                FROM("vsj_member_relation_set  r");
                WHERE("r.platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String updateMemberRelation(VsjMemberRelationSet vsjSysRelationSet) {
            String sql = new SQL() {{
                UPDATE("vsj_member_relation_set");
                SET("relation_switch = #{relationSwitch}");
                SET("need_require = #{needRequire}");
                SET("bind_phone = #{bindPhone}");
                SET("expense_money = #{expenseMoney}");
                SET("expense_num = #{expenseNum}");
                SET("product_ids = #{productIds}");
                SET("product_num = #{productNum}");
                SET("offline_conditions = #{offlineConditions}");
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String saveMemberRelation(VsjMemberRelationSet vsjSysRelationSet) {
            String sql = new SQL() {{
                INSERT_INTO("vsj_member_relation_set");
                VALUES("relation_switch", "#{relationSwitch}");
                VALUES("need_require", "#{needRequire}");
                VALUES("bind_phone", "#{bindPhone}");
                VALUES("expense_money", "#{expenseMoney}");
                VALUES("expense_num", "#{expenseNum}");
                VALUES("product_ids", "#{productIds}");
                VALUES("product_num","#{productNum}");
                VALUES("offline_conditions","#{offlineConditions}");
                VALUES("platform_code", "#{platformCode}");
            }}.toString();
            return sql;
        }
    }


}
