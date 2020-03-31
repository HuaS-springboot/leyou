package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjMemberProfit;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.MemberTeamResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;


@Mapper
public interface VsjMemberProfitMapper {

	public static final String tableName = "vsj_member_profit";

	public static final String baseColumn = "id,member_id,reg_id,first_lower_count,second_lower_count,lower_level_count,first_team_results,second_team_results,all_team_results,complet_order,complet_order_results,settled_wages,no_settled_wages,total_wages,platform_code";
	

	/**
	 * 
	 * @Title: updateWagesByPrimaryKey
	 * @Description: 更新提成信息
	 * @param memberId
	 *            memberId
	 * @param settledWages
	 *            已结算提成
	 * @param noSettledWages
	 *            未结算提成
	 * @param totalwages
	 *            总提成
	 * @return
	 * @author mario
	 * @return: int
	 */
	@UpdateProvider(type = Provider.class, method = "updateWagesByPrimaryKey")
	int updateWagesByPrimaryKey(@Param("member_id")Integer memberId, @Param("settledWages")double settledWages, @Param("noSettledWages")double noSettledWages, @Param("totalwages")double totalwages);

	@SelectProvider(type = VsjMemberProfitMapper.Provider.class, method = "getMemberProfitByMemberId")
    VsjMemberProfit getMemberProfitByMemberId(@Param("memberId") Integer memberId,@Param("platformCode")Integer platformCode);

	@UpdateProvider(type = VsjMemberProfitMapper.Provider.class, method = "updateByMemberId")
    int updateByMemberId(VsjMemberProfit vsjMemberProfit);

	@SelectProvider(type = VsjMemberProfitMapper.Provider.class, method = "getMemberProfitList")
    List<MemberTeamResponse> getMemberProfitList(BaseQueryStat baseQueryStat);

    class Provider {

		public String updateWagesByPrimaryKey(Integer id, double settledWages, double noSettledWages, double totalwages) {
			String sql = new SQL() {
				{
					UPDATE(tableName);
					SET("settled_wages = settled_wages + #{settledWages}");
					SET("no_settled_wages = no_settled_wages + #{noSettledWages}");
					SET("total_wages = total_wages + #{totalWages}");
					WHERE("member_id = #{memberId}");
				}
			}.toString();
			return sql;
		}

		public String getMemberProfitByMemberId(@Param("memberId") Integer memberId,@Param("platformCode")Integer platformCode){
			String sql = new SQL(){{
				SELECT(baseColumn);
				FROM(tableName);
				WHERE("member_id = #{memberId} and platform_code = #{platformCode}");
			}}.toString();
			return sql;
		}

		public String updateByMemberId(VsjMemberProfit vsjMemberProfit){
			String sql = new SQL(){{
				UPDATE("vsj_member_profit");
				if(vsjMemberProfit.getFirstLowerCount() != null){
					SET("first_lower_count = first_lower_count + #{firstLoweCount}");
				}
				if(vsjMemberProfit.getSecondLowerCount() != null){
					SET("second_lower_count = second_lower_count + #{secondLowerCount}");
				}
				if(vsjMemberProfit.getLowerLevelCount() != null){
					SET("lower_level_count = lower_level_count + #{loweLevelCount}");
				}
				WHERE("member_id = #{memberId}");
			}}.toString();
			return sql;
		}

		public String getMemberProfitList(BaseQueryStat baseQueryStat){
			String sql = new SQL(){{
				SELECT("p.id,IFNULL(p.first_lower_count, 0) AS firstLowerCount,IFNULL(p.second_lower_count, 0) AS secondLowerCount");
				SELECT("IFNULL(p.lower_level_count, 0) AS lowerLevelCount,IFNULL(p.first_team_results, 0) AS firstTeamResults");
				SELECT("IFNULL(p.second_team_results, 0) AS secondTeamResults,IFNULL(p.all_team_results, 0) AS allTeamResults");
				SELECT("IFNULL(p.complet_order, 0) AS completOrder,IFNULL(p.complet_order_results, 0) AS completOrderResults");
				SELECT("IFNULL(p.settled_wages, 0) AS settledWages,IFNULL(p.no_settled_wages, 0) AS noSettledWages,r.platform_code");
				SELECT("IFNULL(p.total_wages, 0) AS totalWages,IFNULL(r.head_url, '') AS headUrl,r.tel_phone,r.id AS regId,m.parent_id");
				SELECT("m.join_time,r.nick_name");
				FROM("vsj_register r");
				LEFT_OUTER_JOIN("vsj_member_profit p ON r.id = p.reg_id");
				LEFT_OUTER_JOIN("vsj_member m ON m.id = r.id");
				WHERE(whereBuild(baseQueryStat));
			}}.toString();
			return sql;
		}

		private String whereBuild(BaseQueryStat baseQueryStat){
			List<String> list = new ArrayList<>();
			list.add("r.platform_code = #{platformCode}");
			if(StringUtil.isNoEmptyStr(baseQueryStat.getNickName())){
				list.add("r.nick_name like concat('%',#{nickName},'%')");
			}
			return String.join(" and ", list);
		}
	}

}
