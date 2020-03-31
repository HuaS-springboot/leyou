package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.SysReview;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.SysReviewResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname SysReviewMapper
 * @Description 审核相关mapper
 * @Date 2019/8/21 15:07
 * @Created by wangzx
 */
@Mapper
public interface SysReviewMapper {

    @SelectProvider(type = SysReviewMapper.SysReviewProvider.class,method = "getReviewList")
    List<SysReviewResponse> getReviewList(BaseQueryStat baseQueryStat);

    @UpdateProvider(type = SysReviewMapper.SysReviewProvider.class,method = "updateSysReview")
    int updateSysReview(SysReview sysReview);

    @Select("select id,reg_id,type,withdraw_amount,platform_code,status,create_time,finish_time from vsj_sys_review " +
            "where id = #{id} and platform_code = #{platformCode}")
    SysReview getReviewById(Integer id, Integer platformCode);

    class SysReviewProvider{

        public String getReviewList(BaseQueryStat baseQueryStat){
            String sql = new SQL(){{
                SELECT("r.id,r.reg_id,r.type,r.withdraw_amount,r.`status`,r.create_time,r.finish_time");
                SELECT("g.nick_name,l.level_name,m.level_id,r.platform_code,g.open_id,r.payment_no");
                FROM("vsj_sys_review r");
                LEFT_OUTER_JOIN("vsj_register g ON r.reg_id = g.id");
                LEFT_OUTER_JOIN("vsj_member m ON m.reg_id = g.id");
                LEFT_OUTER_JOIN("vsj_member_level l ON l.id = m.level_id");
                String where = whereBuilder(baseQueryStat);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
                ORDER_BY("r.id");
            }}.toString();
            return sql;
        }

        private String whereBuilder(BaseQueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("r.platform_code = #{platformCode}");
            if(StringUtil.isNoEmptyStr(queryStat.getNickName())){
                list.add("g.nick_name like concat ('%',#{nickName},'%')");
            }
            if(queryStat.getLevelId() != null){
                list.add("m.level_id = #{levelId}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getStartTime())){
                list.add("DATE_FORMAT(r.create_time,'%Y-%m-%d') >= #{startTime}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getEndTime())){
                list.add("DATE_FORMAT(r.create_time,'%Y-%m-%d') <= #{endTime}");
            }
            if(queryStat.getStatus() != null){
                list.add("r.`status` = #{status}");
            }
            return String.join(" and ", list);
        }

        public String updateSysReview(SysReview sysReview){
            String sql = new SQL(){{
                UPDATE("vsj_sys_review");
                if(sysReview.getRegId() != null){
                    SET("reg_id = #{regId}");
                }
                if(sysReview.getType() != null){
                    SET("type = #{type}");
                }
                if(sysReview.getWithdrawAmount() != null){
                    SET("withdraw_amount = #{withdrawAmount}");
                }
                if(sysReview.getStatus() != null){
                    SET("status = #{status}");
                }
                if(StringUtil.isNoEmptyStr(sysReview.getPaymentNo())){
                    SET("payment_no = #{paymentNo}");
                }
                SET("finish_time = NOW()");
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
