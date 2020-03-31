package com.vsj.mapper;

import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import com.vsj.model.VsjStageDistr;

import java.text.MessageFormat;

@Mapper
public interface VsjStageDistrMapper {
	
	public static final String tableName = "vsj_stage_distr";
	
	public static final String baseColumn = "id,level,firstLevelModel,firstLevelValue,secondLevelModel,secondLevelValue,platformCode";
	
	@SelectProvider(type = VsjStageDistrMapper.VsjStageDistrProvider.class, method = "selectByPlatformCode")
	VsjStageDistr selectByPlatformCode(Integer platformCode);

	@InsertProvider(type = VsjStageDistrMapper.VsjStageDistrProvider.class, method = "insertStageDistr")
	int insertStageDistr(VsjStageDistr vsjStageDistr);

	@SelectProvider(type = VsjStageDistrMapper.VsjStageDistrProvider.class, method = "getStageDistrByProductId")
	VsjStageDistr getStageDistrByProductId(BaseQueryStat queryStat);

	@UpdateProvider(type = VsjStageDistrMapper.VsjStageDistrProvider.class, method = "updateStageDistrById")
	int updateStageDistrById(VsjStageDistr vsjStageDistr);

	@Delete("delete from vsj_stage_distr where id = #{id} and platform_code = #{platformCode}")
	int deleteStageDistrById(BaseQueryStat queryStat);

	@Delete("delete from vsj_stage_distr where product_id = #{productId} and platform_code = #{platformCode}")
	void deleteStageDistrByProductId(BaseQueryStat baseQueryStat);

	class VsjStageDistrProvider{
		public String selectByPlatformCode(Integer platformCode) {
			String sql = new SQL() {
				{
					SELECT(baseColumn);
					FROM(tableName);
					WHERE("platform_code = ","#{platformCode}");
				}
			}.toString();
			return sql;
		}

		public String insertStageDistr(VsjStageDistr vsjStageDistr){
			StringBuilder sb = new StringBuilder();
			sb.append("REPLACE INTO vsj_stage_distr (level,first_level_model,first_level_value,second_level_model," +
					"second_level_value,product_id,platform_code) VALUES " );
			String fm =
					"(#{level}, #{firstLevelModel}, #{firstLevelValue},#{secondLevelModel}," +
							"#{secondLevelValue},#{productId},#{platformCode} )" ;
			sb.append(fm);
			return sb.toString();
		}

		public String getStageDistrByProductId(BaseQueryStat queryStat){
			String sql = new SQL(){{
				SELECT("id,level,first_level_model,first_level_value,second_level_model,second_level_value");
				SELECT("product_id,platform_code");
				FROM(tableName);
				WHERE("product_id = #{id} and platform_code = #{platformCode}");
			}}.toString();
			return sql;
		}

		public String updateStageDistrById(VsjStageDistr vsjStageDistr){
			String sql = new SQL(){{
				UPDATE(tableName);
				if(vsjStageDistr.getLevel() != null){
					SET("level = #{level}");
				}
				if(vsjStageDistr.getFirstLevelModel() != null){
					SET("first_level_model = #{firstLevelModel}");
				}
				if(vsjStageDistr.getFirstLevelValue() != null){
					SET("first_level_value = #{firstLevelValue}");
				}
				if(vsjStageDistr.getSecondLevelModel() != null){
					SET("second_level_model = #{secondLevelModel}");
				}
				if(vsjStageDistr.getSecondLevelValue() != null){
					SET("second_level_value = #{secondLevelValue}");
				}
				if(vsjStageDistr.getProductId() != null){
					SET("product_id = #{productId}");
				}
				WHERE("id = #{id} and platform_code = #{platformCode}");
			}}.toString();
			return sql;
		}
	}
	
}
