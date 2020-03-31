package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjRegister;
import com.vsj.model.VsjSysPermission;
import com.vsj.model.VsjSysRole;
import com.vsj.model.VsjSysUser;
import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Date 2019/7/31 17:25
 * @Created by zy
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM vsj_register WHERE id = #{id}")
    VsjRegister getUserById(Integer id);

    @SelectProvider(type = UserMapper.UserProvider.class,method="userLogin")
    @Results({
            @Result(id=true,column = "id",property="id"),//确保映射的条件中：字段与属性一致；如果一致可省略
            @Result(column = "id",/*给第二个sql传的条件数据*/ property = "roles",//映射对象中对应的多的属性名
                    many = @Many(
                            select = "com.vsj.mapper.UserMapper.getRoleList"//要执行的另一个sql语句的路径
                    ))
    })
    VsjSysUser userLogin(@Param("userName")String userName,@Param("passWord")String passWord);

    @Select("SELECT * FROM vsj_sys_user WHERE user_name = #{username}")
    @Results({
            @Result(id=true,column = "id",property="id"),//确保映射的条件中：字段与属性一致；如果一致可省略
            @Result(column = "id",/*给第二个sql传的条件数据*/ property = "roles",//映射对象中对应的多的属性名
                    many = @Many(
                            select = "com.vsj.mapper.UserMapper.getRoleList"//要执行的另一个sql语句的路径
                    ))
    })
    VsjSysUser findByUserName(String username);

    @Select("SELECT r.* FROM vsj_sys_role r LEFT JOIN vsj_sys_user_role ur ON ur.rid = r.id WHERE ur.uid = #{id}")
    List<VsjSysRole> getRoleList(Integer id);

    @SelectProvider(type=UserProvider.class,method = "getSysUserList")
    @Results({
            @Result(id=true,column = "id",property="id"),//确保映射的条件中：字段与属性一致；如果一致可省略
            @Result(column = "id",/*给第二个sql传的条件数据*/ property = "roles",//映射对象中对应的多的属性名
                    many = @Many(
                            select = "com.vsj.mapper.UserMapper.getRoleList"//要执行的另一个sql语句的路径
                    ))
    })
    List<VsjSysUser> getSysUserList(BaseQueryStat baseQueryStat);

    @InsertProvider(type = UserMapper.UserProvider.class,method = "addSysUser")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int addSysUser(VsjSysUser sysUser);

    @UpdateProvider(type = UserMapper.UserProvider.class,method = "updateSysUser")
    int updateSysUser(VsjSysUser sysUser);

    @Delete("DELETE FROM  vsj_sys_user WHERE platform_code = #{platformCode} and FIND_IN_SET(id,#{ids})")
    int delSysUser(BaseQueryStat baseQueryStat);

    @Select("SELECT * FROM vsj_sys_user WHERE  platform_code = #{platformCode} and id = #{id}")
    @Results({
            @Result(id=true,column = "id",property="id"),//确保映射的条件中：字段与属性一致；如果一致可省略
            @Result(column = "id",/*给第二个sql传的条件数据*/ property = "roles",//映射对象中对应的多的属性名
                    many = @Many(
                            select = "com.vsj.mapper.UserMapper.getRoleList"//要执行的另一个sql语句的路径
                    ))
    })
    VsjSysUser getSysUserById(BaseQueryStat baseQueryStat);

    @Delete("DELETE FROM vsj_sys_user_role WHERE platform_code = #{platformCode} and FIND_IN_SET(uid,#{ids})")
    void delSysUsrAndRole(BaseQueryStat baseQueryStat);

    @Delete("DELETE FROM vsj_sys_role_permission WHERE rid = #{id}")
    int delRoleAndPermission(Integer id);

    @Insert({"<script>",
            "insert into vsj_sys_user_role(uid, rid, platform_code) values ",
            "<foreach collection='sets' item='item' separator=','>",
            "(#{id}, #{item.id},#{platformCode})",
            "</foreach>",
            "</script>"})
    int addUserROleJoin(@Param("id") Integer id,@Param("sets") Set<VsjSysRole> sets,@Param("platformCode") Integer platformCode);

    @Select("SELECT * FROM vsj_sys_permission WHERE platform_code = #{platformCode}")
    List<VsjSysPermission> getPermissionList(Integer platformCode);

    class UserProvider{

        public String userLogin(@Param("userName")String userName,@Param("passWord")String passWord){
            String sql = new SQL(){{
                SELECT("*");
                FROM("vsj_sys_user");
                WHERE("account = #{userName} and pswd = #{passWord}");
            }}.toString();
            return sql;
        }

        public String getSysUserList(BaseQueryStat baseQueryStat){
            String sql = new SQL(){{
                SELECT("*");
                FROM("vsj_sys_user");
                String where = whereBuilder(baseQueryStat);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
                ORDER_BY("create_time DESC");
            }}.toString();
            return sql;
        }
        private String whereBuilder(BaseQueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("platform_code = #{platformCode} ");
            if(queryStat.getFuzzyList() != null){
                list.add("user_name LIKE concat ('%',#{fuzzyList},'%') OR nick_name LIKE concat ('%',#{fuzzyList},'%') OR phone LIKE concat ('%',#{fuzzyList},'%')");
            }

            return String.join(" and ", list);
        }
        public String addSysUser(VsjSysUser sysUser){
            String sql = new SQL(){{
                INSERT_INTO("vsj_sys_user");
                if(StringUtil.isNoEmptyStr(sysUser.getNickName())){
                    VALUES("nick_name","#{nickName}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getEmail())){
                    VALUES("email","#{email}");
                }
                if(sysUser.getCreateId() != null){
                    VALUES("create_id","#{createId}");
                }
                if(sysUser.getState() != null) {
                    VALUES("state", "#{state}");
                }
                if(sysUser.getSex() != null){
                    VALUES("sex","#{sex}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getAccount())){
                    VALUES("account","#{account}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getPhone())){
                    VALUES("phone","#{phone}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getPswd())){
                    VALUES("pswd","#{pswd}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getImgUrl())){
                    VALUES("img_url","#{imgUrl}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getRemark())){
                    VALUES("remark","#{remark}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }
        public String updateSysUser(VsjSysUser sysUser){
            String sql = new SQL(){{
                UPDATE("vsj_sys_user");
                if(StringUtil.isNoEmptyStr(sysUser.getNickName())){
                    SET("nick_name = #{nickName}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getEmail())){
                    SET("email = #{email}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getAccount())){
                    SET("account = #{account}");
                }
                if(sysUser.getModifier() != null){
                    SET("modifier = #{modifier}");
                }
                if(sysUser.getSex() != null){
                    SET("sex = #{sex}");
                }
                if(sysUser.getState() != null) {
                    SET("state = #{state}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getPhone())){
                    SET("pswd = #{pswd}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getImgUrl())){
                    SET("img_url = #{imgUrl}");
                }
                if(StringUtil.isNoEmptyStr(sysUser.getRemark())){
                    SET("remark = #{remark}");
                }
                SET("last_modify_time = NOW()");
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
