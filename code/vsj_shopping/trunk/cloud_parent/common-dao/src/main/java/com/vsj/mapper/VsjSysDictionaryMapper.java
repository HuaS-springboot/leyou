package com.vsj.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import com.vsj.model.VsjSysDictionary;

@Mapper
public interface VsjSysDictionaryMapper {
	public static final String tableName = "vsj_sys_dictionary";

	public static final String baseColumn = "`id`,`type`,`key`,`value`";
	
	@SelectProvider(type = Provider.class, method = "selectByType")
	List<VsjSysDictionary> selectByType(String type);
	
	class Provider {
		public String selectByType(String type) {
			String sql = new SQL() {
				{
					SELECT("`key`,`value`");
					FROM(tableName);
					WHERE("`type` = #{type}");
				}
			}.toString();
			return sql;
		}
	}
}