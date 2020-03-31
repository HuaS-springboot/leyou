package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjActivity;
import com.vsj.model.VsjActivityGroup;
import com.vsj.model.VsjActivitySeckill;
import com.vsj.model.VsjProductSpecs;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ActivityResponse;
import com.vsj.model.response.ActivitySpecsResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname ActivityMapper
 * @Description TODO
 * @Date 2019/7/29 14:54
 * @Created by zy
 */
@Mapper
public interface ActivityMapper {

    @SelectProvider(type =ActivityMapper.ActivityProvider.class,method = "getActivityList")
    List<VsjActivity> getActivityList(BaseQueryStat queryStat);

    @Select("SELECT * FROM vsj_activity WHERE id = #{id} and platform_code = #{platformCode}")
    @Results({
            @Result(id=true,column = "id",property="id"),
            @Result(column="{id=id,platformCode = platform_code}",property="vsjActivityGroup",
                    many=@Many(
                            select = "com.vsj.mapper.ActivityMapper.getVsjActivityGroupList"
                    ))
    })
    ActivityResponse getActivityById(BaseQueryStat queryStat);

    @Select("SELECT * FROM vsj_activity_group WHERE activity_id = #{id} and platform_code =#{platformCode}")
    List<VsjActivityGroup> getVsjActivityGroupList(@Param("id") Integer id,@Param("platformCode")Integer platformCode);
//====================================================================================================

    @Select("SELECT * FROM vsj_activity WHERE id = #{id}")
    @Results({
            @Result(id=true,column = "id",property="id"),//确保映射的条件中：字段与属性一致；如果一致可省略
            @Result(column = "{id=id,platformCode = platform_code}",/*给第二个sql传的条件数据*/ property = "vsjActivitySeckills",//映射对象中对应的多的属性名
                    many = @Many(
                            select = "com.vsj.mapper.ActivityMapper.getActivitySeckillList"//要执行的另一个sql语句的路径
                    ))
    })
    ActivityResponse getActivitySeckillById(BaseQueryStat queryStat);//总映射接口必须写在这两个关联的sql语句中间

    @Select("SELECT * FROM vsj_activity_seckill WHERE activity_id = #{id} and platform_code = #{platformCode}")
    List<VsjActivitySeckill> getActivitySeckillList(@Param("id") Integer id,@Param("platformCode")Integer platformCode);

//====================================================================================================
    @Select("SELECT * FROM vsj_product_specs WHERE product_id = #{id} and platform_code = #{platformCode}")
    List<VsjProductSpecs> getSpecsList(BaseQueryStat queryStat);

    @Insert("insert into vsj_activity (product_id,activity_name,start_time,end_time,create_time,product_name,sys_user_id,nick_name,platform_code,activity_type) values (#{productId},#{activityName},#{startTime},#{endTime},NOW(),#{productName},#{sysUserId},#{nickName},#{platformCode},#{activityType})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertActivity(ActivityResponse activityResponse);

    @Insert({
            "<script>",
            "insert into vsj_activity_group(activity_id, product_number, quota_number,group_number,activity_price,specs_id,create_time,product_price,product_stock,attr_json,platform_code) values ",
            "<foreach collection='vsjActivityGroup' item='item' index='index' separator=','>",
            "(#{item.activityId}, #{item.productNumber}, #{item.quotaNumber},#{item.groupNumber},#{item.activityPrice},#{item.specsId},NOW(),#{item.productPrice},#{item.productStock},#{item.attrJson},#{item.platformCode})",
            "</foreach>",
            "</script>"
    })
    int insertActivityGroup(@Param("vsjActivityGroup") List<VsjActivityGroup> vsjActivityGroup);

    @Update("UPDATE vsj_activity SET activity_status = #{status} WHERE platform_code = #{platformCode} and id = #{id}")
    int updateActivity(BaseQueryStat baseQueryStat);

    @Insert({
            "<script>",
            "insert into vsj_activity_seckill(activity_id, product_number, quota_number,activity_price,specs_id,create_time,product_price,product_stock,attr_json,platform_code) values ",
            "<foreach collection='vsjActivitySeckills' item='item' index='index' separator=','>",
            "(#{item.activityId}, #{item.productNumber}, #{item.quotaNumber},#{item.activityPrice},#{item.specsId},NOW(),#{item.productPrice},#{item.productStock},#{item.attrJson},#{item.platformCode})",
            "</foreach>",
            "</script>"
    })
    int insertActivitySeckills(@Param("vsjActivitySeckills")List<VsjActivitySeckill> vsjActivitySeckills);

    @UpdateProvider(type =ActivityMapper.ActivityProvider.class,method = "updateActivityAll")
    int updateActivityAll(ActivityResponse activityResponse);

    @UpdateProvider(type =ActivityMapper.ActivityProvider.class,method = "vsjActivityGroup")
    int updateActivityGroup(ActivitySpecsResponse activitySpecsResponse);

    @UpdateProvider(type =ActivityMapper.ActivityProvider.class,method = "vsjActivitySeckills")
    int updateActivitySeckills(ActivitySpecsResponse activitySpecsResponse);

    @Select("SELECT product_id FROM vsj_activity WHERE platform_code = #{platformCode} AND id=#{id}")
    ActivityResponse getProductId(ActivityResponse activityResponse);

    @Delete("DELETE FROM vsj_activity_group WHERE platform_code = #{platformCode} AND activity_id = #{id}")
    int delActivityGroupById(ActivityResponse activityResponse);

    @Delete("DELETE FROM vsj_activity_seckill WHERE platform_code = #{platformCode} AND activity_id = #{id}")
    int delActivitySeckillsById(ActivityResponse activityResponses);

    @Delete("DELETE FROM vsj_activity_group WHERE platform_code = #{platformCode} AND id = #{id}")
    int delActivityGroup(ActivitySpecsResponse activitySpecsResponse);

    @Delete("DELETE FROM vsj_activity_seckill WHERE platform_code = #{platformCode} AND id = #{id}")
    int delActivitySeckills(ActivitySpecsResponse activitySpecsResponse);

    @Delete("DELETE FROM vsj_activity WHERE platform_code = #{platformCode} AND id = #{id}")
    int delActivity(ActivityResponse activityResponses);

//    @SelectProvider(type=ActivityMapper.ActivityProvider.class,method="getActivitySeckill")
//    List<ActivityResponse> getActivitySeckill();

    class ActivityProvider{
//        public String getActivitySeckill(){
//            String sql = new SQL(){{
//                SELECT("a.activity_name,a.product_id,a.start_time,a.end_time,ae.attr_json,ae.product_stock,ae.product_number,ae.product_price,ae.activity_price");
//                FROM("vsj_activity a");
//                LEFT_OUTER_JOIN("vsj_activity_group ae ON a.id = ae.activity_id");
//                WHERE("ae.activity_id=13");
//            }}.toString();
//            return sql;
//        }

        public String getActivityList(BaseQueryStat queryStat){
            String sql=new SQL(){
                {
                    SELECT("a.id,a.product_id,a.activity_type,a.activity_name,a.start_time,a.end_time");
                    SELECT("a.activity_status,a.participate_number,a.product_name,a.sys_user_id,a.nick_name");
                    SELECT("p.product_image,a.create_time");
                    FROM("vsj_activity a");
                    LEFT_OUTER_JOIN("vsj_product p ON a.product_id = p.product_id");
                    String where = whereBuilder(queryStat);
                    if(StringUtil.isNoEmptyStr(where)){
                        WHERE(where);
                    }
                    ORDER_BY("create_time DESC");
                }
            }.toString();
            return sql;
        }
        private String whereBuilder(BaseQueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("a.platform_code = #{platformCode}");
            if(queryStat.getType() != null){
                list.add("a.activity_type = #{type}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getTitle())){
                list.add("a.activity_name like concat ('%',#{title},'%')");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getProductName())){
                list.add("p.product_name like concat ('%',#{productName},'%')");
            }
            if(queryStat.getStatus() != null){
                list.add("a.activity_status = #{status}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getStartTime())){
                list.add("a.create_time >= #{startTime}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getEndTime())){
                list.add("a.create_time <= #{endTime}");
            }
            return String.join(" and ", list);
        }
        public String updateActivityAll(ActivityResponse activityResponse){
            String sql = new SQL(){{
                UPDATE("vsj_activity");
                if(StringUtil.isNoEmptyStr(activityResponse.getActivityName())){
                    SET("activity_name = #{activityName}");
                }
                if(StringUtil.isNoEmptyStr(activityResponse.getStartTime())){
                    SET("start_time = #{startTime}");
                }
                if(StringUtil.isNoEmptyStr(activityResponse.getEndTime())){
                    SET("end_time = #{endTime}");
                }
                if(activityResponse.getProductId() != null){
                    SET("product_id = #{productId}");
                }
                if(activityResponse.getActivityStatus() != null){
                    SET("activity_status = #{activityStatus}");
                }
                if(activityResponse.getParticipateNumber() != null) {
                    SET("participate_number = #{participateNumber}");
                }
                if(StringUtil.isNoEmptyStr(activityResponse.getProductName())){
                    SET("product_name = #{productName}");
                }
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
        public String vsjActivityGroup(ActivitySpecsResponse activitySpecsResponse){
            String sql = new SQL(){{
                UPDATE("vsj_activity_group");
                if(StringUtil.isNoEmptyStr(activitySpecsResponse.getAttrJson())){
                    SET("attr_json = #{attrJson}");
                }
                if(activitySpecsResponse.getProductNumber() != null){
                    SET("product_number = #{productNumber}");
                }
                if(activitySpecsResponse.getQuotaNumber() != null){
                    SET("quota_number = #{quotaNumber}");
                }
                if(activitySpecsResponse.getGroupNumber() != null){
                    SET("group_number = #{groupNumber}");
                }
                if(activitySpecsResponse.getActivityPrice() != null) {
                    SET("activity_price = #{activityPrice}");
                }
                if(activitySpecsResponse.getSpecsId() != null){
                    SET("specs_id = #{specsId}");
                }
                if(activitySpecsResponse.getProductStock() != null){
                    SET("product_stock = #{productStock}");
                }

                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
        public String vsjActivitySeckills(ActivitySpecsResponse activitySpecsResponse){
            String sql = new SQL(){{
                UPDATE("vsj_activity_seckill");
                if(StringUtil.isNoEmptyStr(activitySpecsResponse.getAttrJson())){
                    SET("attr_json = #{attrJson}");
                }
                if(activitySpecsResponse.getProductNumber() != null){
                    SET("product_number = #{productNumber}");
                }
                if(activitySpecsResponse.getQuotaNumber() != null){
                    SET("quota_number = #{quotaNumber}");
                }
                if(activitySpecsResponse.getActivityPrice() != null) {
                    SET("activity_price = #{activityPrice}");
                }
                if(activitySpecsResponse.getSpecsId() != null){
                    SET("specs_id = #{specsId}");
                }
                if(activitySpecsResponse.getProductStock() != null){
                    SET("product_stock = #{productStock}");
                }

                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
