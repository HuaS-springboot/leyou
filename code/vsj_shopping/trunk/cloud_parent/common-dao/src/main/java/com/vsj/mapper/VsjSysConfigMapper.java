package com.vsj.mapper;

import com.vsj.model.VsjSysConfig;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Mapper
public interface VsjSysConfigMapper {

	public static final String tableName = "vsj_sys_config";

	public static final String baseColumn = "`key`,`value`,`platform_code`";

	/**
	 * 
	 * @Title: batchInsert
	 * @Description: 批量插入或修改
	 * @param vsjSysConfigs
	 * @return
	 * @author mario
	 * @return: String
	 */
	@InsertProvider(type = Provider.class, method = "batchInsert")
	int batchInsert(List<VsjSysConfig> vsjSysConfigs);

	/**
	 * 
	 * @Title: selectByKey
	 * @Description: 根据Key查询对应的信息
	 * @param key
	 * @param platformCode
	 * @return
	 * @author mario
	 * @return: String
	 */
	@SelectProvider(type = Provider.class, method = "selectByKey")
	VsjSysConfig selectByKey(@Param("key")String key, @Param("platformCode")Integer platformCode);

	class Provider {

		public String batchInsert(Map map) {
			List<VsjSysConfig> vsjSysConfigs = (List<VsjSysConfig>) map.get("list");
			StringBuilder sb = new StringBuilder();
			sb.append("REPLACE INTO vsj_sys_config (`key`,`value`,`platform_code`) VALUES ");
			MessageFormat mf = new MessageFormat(
					"(#'{'list[{0}].key}, #'{'list[{0}].value}, #'{'list[{0}].platformCode})");
			for (int i = 0; i < vsjSysConfigs.size(); i++) {
				sb.append(mf.format(new Object[] { i }));
				if (i < vsjSysConfigs.size() - 1)
					sb.append(",");
			}
			return sb.toString();
		}

		public String selectByKey(@Param("key")String key,@Param("platformCode") Integer platformCode) {
			String sql = new SQL() {
				{
					SELECT(baseColumn);
					FROM(tableName);
					WHERE("`key` = #{key} and `platform_code` = #{platformCode}");
				}
			}.toString();
			return sql;
		}
	}
}
