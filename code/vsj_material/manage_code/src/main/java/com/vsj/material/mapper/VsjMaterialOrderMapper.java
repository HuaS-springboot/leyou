package com.vsj.material.mapper;

import com.sun.org.apache.regexp.internal.RE;
import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.MaterialOrder;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.VsjMaterialOrderResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjMaterialOrderMapper
 * @Description 订单相关Mapper
 * @Date 2019/8/15 9:16
 * @Created by wangzx
 */
@Mapper
public interface VsjMaterialOrderMapper {

    @SelectProvider(type = VsjMaterialOrderMapper.VsjMaterialOrderProvider.class,method = "getMemberOrderList")
    List<VsjMaterialOrderResponse> getMemberOrderList(QueryStat queryStat);

    @InsertProvider(type = VsjMaterialOrderMapper.VsjMaterialOrderProvider.class,method = "insertMaterialOrder")
    int insertMaterialOrder(MaterialOrder materialOrder);

    @Select("select id,order_no,reg_id,package_id,pay_amount,order_pay_type,order_pay_no where order_no = #{orderNo}" +
            " AND platform_code= #{platformCode}")
    MaterialOrder getMaterialOrderByOrderNo(String orderNo, Integer platformCode);

    @UpdateProvider(type = VsjMaterialOrderMapper.VsjMaterialOrderProvider.class,method = "updateMaterial")
    int updateMaterial(MaterialOrder materialOrder);

    class VsjMaterialOrderProvider{

        public String getMemberOrderList(QueryStat queryStat){
            String sql = new SQL(){{
                SELECT("o.id,o.order_no,o.reg_id,o.package_id,o.pay_amount,o.create_time,o.platform_code");
                SELECT("m.expired_time,m.level_id,l.level_name,r.head_url,r.nick_name,r.tel_phone");
                FROM("vsj_material_order o");
                LEFT_OUTER_JOIN("vsj_material_member m ON o.reg_id = m.reg_id");
                LEFT_OUTER_JOIN("vsj_material_member_level l ON m.level_id = l.id");
                LEFT_OUTER_JOIN("vsj_material_register r ON r.id = m.reg_id");
                String where = whereBuilder(queryStat);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
                ORDER_BY("o.id DESC");
            }}.toString();
            return sql;
        }

        private String whereBuilder(QueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("o.platform_code = #{platformCode}");
            if(StringUtil.isNoEmptyStr(queryStat.getNickName())){
                list.add("r.nick_name like concat('%',#{nickName},'%')");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getTelPhone())){
                list.add("r.tel_phone like concat('%',#{telPhone},'%')");
            }
            if(queryStat.getLevelId() != null){
                list.add("m.level_id = #{levelId}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getOrderNo())){
                list.add("o.order_no like concat('%',#{orderNo},'%')");
            }
            return String.join(" and ", list);
        }

        public String insertMaterialOrder(MaterialOrder materialOrder){
            String sql = new  SQL(){{
                INSERT_INTO("vsj_material_order");
                VALUES("order_no","#{orderNo}");
                VALUES("reg_id","#{regId}");
                VALUES("package_id","#{packageId}");
                VALUES("pay_amount","#{payAmount}");
                VALUES("order_pay_type","#{orderPayType}");
                VALUES("order_pay_no","#{orderPayNo}");
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String updateMaterial(MaterialOrder materialOrder){
            String sql = new SQL(){{
                UPDATE("vsj_material_order");
                if(StringUtil.isNoEmptyStr(materialOrder.getOrderNo())){
                    SET("order_no = #{orderNo}");
                }
                if(materialOrder.getRegId() != null){
                    SET("reg_id = #{regId}");
                }
                if(materialOrder.getPackageId() != null){
                    SET("package_id = #{packageId}");
                }
                if(materialOrder.getPayAmount() != null){
                    SET("pay_amount = #{payAmount}");
                }
                if(materialOrder.getOrderPayType() != null){
                    SET("order_pay_type = #{orderPayType}");
                }
                if(StringUtil.isNoEmptyStr(materialOrder.getOrderPayNo())){
                    SET("order_pay_no = #{orderPayNo}");
                }
                if(materialOrder.getStatus() != null){
                    SET("`status` = #{status}");
                }
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

    }
}
