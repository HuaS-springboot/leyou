package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import com.vsj.model.VsjMember;

import java.util.List;

@Mapper
public interface VsjMemberMapper {

	public static final String tableName = "vsj_member";

	public static final String baseColumn = "id,reg_id,reg_name,join_time,level_id,parent_id,platform_code";
	
	@SelectProvider(type = VsjMemberMapper.VsjMemberProvider.class, method = "selectByPrimaryKey")
	VsjMember selectByPrimaryKey(Integer id);

	@UpdateProvider(type = VsjMemberMapper.VsjMemberProvider.class, method = "updateVsjMember")
    int updateVsjMember(VsjMember vsjMember);

	@SelectProvider(type = VsjMemberMapper.VsjMemberProvider.class, method = "selectByRegId")
	VsjMember selectByRegId(Integer regId);

	@SelectProvider(type = VsjMemberMapper.VsjMemberProvider.class, method = "getTotalSuperMemberIds")
    List<Integer> getTotalSuperMemberIds(Integer id);

    class VsjMemberProvider {

		public String selectByPrimaryKey(Integer id) {
			String sql = new SQL() {
				{
					SELECT(baseColumn);
					FROM(tableName);
					WHERE("id = #{id}");
				}
			}.toString();
			return sql;
		}

		public String updateVsjMember(VsjMember vsjMember){
			String sql = new SQL(){{
				UPDATE("vsj_member");
				if(vsjMember.getLevelId() != null){
					SET("level_id = #{levelId}" );
				}
				if(vsjMember.getParentId() != null){
					SET("parent_id = #{parentId}" );
				}
				if(StringUtil.isNoEmptyStr(vsjMember.getParentName())){
					SET("parent_name = #{parentName}" );
				}
				if(StringUtil.isNoEmptyStr(vsjMember.getRegName())){
					SET("reg_name = #{regName}" );
				}
				WHERE("id = #{id} and platform_code = #{platformCode}");
			}}.toString();
			return sql;
		}

		public String selectByRegId(Integer regId) {
			String sql = new SQL() {
				{
					SELECT(baseColumn);
					FROM(tableName);
					WHERE("reg_id = ", "#{regId}");
				}
			}.toString();
			return sql;
		}

		public String getTotalSuperMemberIds(Integer id){
			String sql = new SQL(){{
				SELECT("SELECT T2.id");
				FROM("( SELECT @r AS _id,(SELECT @r := parent_id FROM vsj_member WHERE id = _id) AS parent_id, " +
						"@l := @l + 1 AS lvl FROM (SELECT @r := #{id}, @l := 0) vars, vsj_member h WHERE @r <> 0 ) T1");
				JOIN("vsj_member T2 ON T1._id = T2.id");
				ORDER_BY("ORDER BY T1.lvl DESC");

			}}.toString();
			return sql;
		}
	}

}
