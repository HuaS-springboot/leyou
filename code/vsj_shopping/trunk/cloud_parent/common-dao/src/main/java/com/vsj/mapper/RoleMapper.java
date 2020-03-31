package com.vsj.mapper;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjSysPermission;
import com.vsj.model.VsjSysRole;
import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Set;

/**
 * @Classname RoleMapper
 * @Description TODO
 * @Date 2019/8/20 17:18
 * @Created by zy
 */
@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM vsj_sys_role WHERE platform_code = #{platformCode}")
    List<VsjSysRole> getRoleList(Integer platformCode);

    @Select("SELECT * FROM  vsj_sys_role WHERE  platform_code = #{platformCode} AND id = #{id}")
    @Results({
            @Result(id=true,column = "id",property="id"),//确保映射的条件中：字段与属性一致；如果一致可省略
            @Result(column = "id",/*给第二个sql传的条件数据*/ property = "permission",//映射对象中对应的多的属性名
                    many = @Many(
                            select = "com.vsj.mapper.SysPermissionMapper.getPermissionsByRoleId"//要执行的另一个sql语句的路径
                    ))
    })
    VsjSysRole getRoleById(BaseQueryStat baseQueryStat);


    @Delete("DELETE FROM vsj_sys_role WHERE platform_code = #{platformCode} and FIND_IN_SET(id,#{ids})")
    int delRole(BaseQueryStat baseQueryStat);

    @Update("UPDATE FROM vsj_sys_role SET role_name = #{roleName} WHERE platform_code = #{platformCode} and id = #{id}")
    int editRole(VsjSysRole vsjSysRole);

    @InsertProvider(type =RoleMapper.RoleProvider.class,method = "insertRole")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertRole(VsjSysRole vsjSysRole);

    @Insert({"<script>",
            "insert into vsj_sys_role_permission(rid,pid,platform_code) values ",
            "<foreach collection='permissions' item='item' separator=',' >",
            "(#{id}, #{item.id},#{platformCode})",
            "</foreach>",
            "</script>"})
    int addUserROleJoin(@Param("id") Integer id,@Param("platformCode")Integer platformCode, @Param("permissions") Set<VsjSysPermission> permissions);

    class RoleProvider{

        public String insertRole(VsjSysRole vsjSysRole){
            String sql = new SQL(){{
                INSERT_INTO("vsj_sys_role");
                if(StringUtil.isNoEmptyStr(vsjSysRole.getRoleName())){
                    VALUES("role_name","#{roleName}");
                }
                if(StringUtil.isNoEmptyStr(vsjSysRole.getRoleCode())){
                    VALUES("role_code","#{roleCode}");
                }
                if(vsjSysRole.getState() != null){
                    VALUES("state","#{state}");
                }
                if(vsjSysRole.getCreateId() != null){
                    VALUES("create_id","#{createId}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

    }
}
