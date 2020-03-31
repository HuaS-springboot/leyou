package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.RegisterRecord;
import com.vsj.model.VsjMemberProfit;
import com.vsj.model.VsjOrderReceiptsRecord;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.MemberResponse;
import com.vsj.model.response.UserBalanceResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/8/2 16:56
 * @Describe:会员管理
 */
@Mapper
public interface MemberMapper {

    @SelectProvider(type = MemberMapper.MemberProvider.class,method="getMemberList")
    List<MemberResponse> getMemberList(BaseQueryStat queryStat);

    @SelectProvider(type = MemberMapper.MemberProvider.class,method="getBalanceDeTail")
    UserBalanceResponse getBalanceDeTail(@Param("userId")Integer userId);

    @SelectProvider(type = MemberMapper.MemberProvider.class,method = "getTransactionDetail")
    RegisterRecord getTransactionDetail(@Param("userId")Integer userId);

    @SelectProvider(type = MemberMapper.MemberProvider.class,method = "memberProfit")
    VsjMemberProfit memberProfit(@Param("userId")Integer userId);

    @SelectProvider(type = MemberMapper.MemberProvider.class,method = "orderReceiptsRecord")
    List<VsjOrderReceiptsRecord> orderReceiptsRecord(@Param("userId")Integer userId,@Param("isSettle")Integer isSettle);

    class MemberProvider{
        public String orderReceiptsRecord(Integer userId,Integer isSettle){
            String sql = new SQL(){{
                SELECT("o.order_no,o.order_amount_total,o.order_status,o.create_time as order_time,ml.level_name");//,r.id
                SELECT("orr.create_time,orr.income_source,orr.is_settle,orr.member_id,orr.operation_money,orr.order_id,orr.platform_code,orr.type");
                FROM("vsj_order_receipts_record orr");
                LEFT_OUTER_JOIN("vsj_order o ON orr.order_id = o.order_id");
                LEFT_OUTER_JOIN("vsj_register r ON o.reg_id = r.id");
                LEFT_OUTER_JOIN("vsj_member_level ml ON orr.level_id = ml.id");
                String where = whereBuilder(userId,isSettle);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
//                WHERE("r.id = #{userId} and orr.is_settle = #{isSettle}");
            }}.toString();
            return sql;
        }

        public String whereBuilder(Integer userId,Integer isSettle){
            List<String> list = new ArrayList<>();
            if(userId != null){
                list.add("r.id = #{userId}");
            }
            if(isSettle != null){
                list.add("orr.is_settle = #{isSettle}");
            }
            return String.join(" and ",list);
        }

        public String memberProfit(Integer userId){
            String sql = new SQL(){{
                SELECT("r.id,mp.settled_wages,mp.no_settled_wages,mp.total_wages");
                FROM("vsj_member_profit mp");
                LEFT_OUTER_JOIN("vsj_register r ON r.id = mp.reg_id");
                WHERE("r.id = #{userId}");
            }}.toString();
            return sql;
        }

        public String getTransactionDetail(Integer userId){
            String sql = new SQL(){{
                SELECT("re. create_time,re.carry_balance,re.platform_code,re.source,re.total_balance,re.type");
                FROM("vsj_register_record re");
                LEFT_OUTER_JOIN("vsj_register r ON r.id = re.reg_id");
                WHERE("re.reg_id = #{userId}");
            }}.toString();
            return sql;
        }

        public String getBalanceDeTail(Integer userId){
            String sql = new SQL(){{
                SELECT("mp.no_settled_wages,mp.settled_wages");
                SELECT("r.carry_balance,r.freeze_balance");
                FROM("vsj_member_profit mp");
                LEFT_OUTER_JOIN("vsj_register r ON mp.reg_id = r.id");
                WHERE("r.id = #{userId}");//and r.platform_code = #{platformCode}
            }}.toString();
            return sql;
        }

        public String getMemberList(BaseQueryStat queryStat){
            String sql = new SQL(){{
                SELECT("m.id,m.reg_id,m.parent_id,m.parent_name,m.join_time,m.level_id");
                SELECT("r.nick_name,r.head_url,r.sex,r.tel_phone,r.carry_balance,r.freeze_balance,r.remark");
                SELECT("ml.level_name");
                FROM("vsj_member m");
                LEFT_OUTER_JOIN("vsj_register r on m.reg_id = r.id");
                LEFT_OUTER_JOIN("vsj_member_level ml on m.level_id = ml.id");
                String where = whereBuild(queryStat);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
                ORDER_BY("m.join_time DESC");
            }}.toString();
            return sql;
        }

        private String whereBuild(BaseQueryStat queryStat){
            List<String> list = new ArrayList<>();
            if(queryStat.getId() != null){
                list.add("m.id = #{id}");
            }
            if(queryStat.getParentId() != null){
                list.add("m.parent_id = #{parentId}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getParentName())){
                list.add("m.parent_name like CONCAT('%',#{parentName},'%')");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getStartTime())){
                list.add("m.join_time >= #{startTime}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getEndTime())){
                list.add("m.join_time <= #{endTime}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getNickName())){
                list.add("r.nick_name like CONCAT('%',#{nickName},'%')");
            }
            if(null != queryStat.getLevelId()){
                list.add("m.level_id = #{levelId}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getTelPhone())){
                list.add("r.tel_phone like CONCAT('%',#{telPhone},'%')");
            }
            list.add("m.platform_code = #{platformCode}");
            return String.join(" and ",list);
        }

    }

}
