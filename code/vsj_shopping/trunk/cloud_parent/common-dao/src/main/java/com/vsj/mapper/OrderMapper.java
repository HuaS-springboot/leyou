package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjOrder;
import com.vsj.model.VsjOrderDetail;
import com.vsj.model.VsjOrderReceiptsRecord;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.OrderResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname OrderMapper
 * @Description 订单管理mapper接口
 * @Date 2019/7/24 16:23
 * @Created by zy
 */
@Mapper
public interface OrderMapper {

    @SelectProvider(type=OrderMapper.OrderProvider.class,method="getOrderList")
    //查询订单列表
    List<OrderResponse> getOrderList(BaseQueryStat queryStat);


    //查询订单详情列表
    @Select("SELECT * FROM vsj_order_detail WHERE order_id = #{order_id}")
    List<VsjOrderDetail> getVsjOrderDetailList(Integer order_id);

    @SelectProvider(type=OrderMapper.OrderProvider.class,method="getOrderList")
    @Results({
            @Result(id=true,column = "order_id",property="orderId"),
            @Result(column="order_id",property="orderDetail",
                    many=@Many(
                            select = "com.vsj.mapper.OrderMapper.getVsjOrderDetailList"
                    )),
            @Result(column="order_id",property="orderRecord",
                    many=@Many(
                            select = "com.vsj.mapper.OrderMapper.getVsjOrderRecordList"
                    ))
    })
    //查询订单详情信息
    OrderResponse getOrderDetailsList(BaseQueryStat queryStat);

    //查询订单奖励详情
    @Select("SELECT r.id,r.member_id,rs.nick_name,m.level_id,l.level_name,r.operation_money,m.join_time,m.parent_id,m.parent_name,rs.carry_balance,rs.tel_phone,rs.remark " +
            "FROM vsj_order_receipts_record r LEFT JOIN vsj_member m ON r.member_id=m.id " +
            "LEFT JOIN vsj_member_level l ON m.level_id=l.id LEFT JOIN vsj_register rs ON rs.id=m.reg_id " +
            "WHERE  order_id = #{order_id}")
    List<VsjOrderReceiptsRecord> getVsjOrderRecordList(Integer order_id);

    @UpdateProvider(type=OrderMapper.OrderProvider.class,method="updateOrder")
    //修改订单状态
    int updateOrderStatus(BaseQueryStat queryStat);
    //
    @Select("SELECT\n" +
            "\to.order_id,\n" +
            "\to.order_no,\n" +
            "\to.product_name,\n" +
            "\tr.nick_name,\n" +
            "\to.pay_channel,\n" +
            "\to.product_amount_total,\n" +
            "\to.order_amount_total,\n" +
            "\to.create_time,\n" +
            "\to.pay_time,\n" +
            "\to.order_status\n" +
            "FROM\n" +
            "\tvsj_order o\n" +
            "LEFT JOIN vsj_register r ON r.id = o.reg_id\n" +
            "WHERE \n" +
            " o.platform_code = #{platformCode} and FIND_IN_SET(o.order_id,#{ids}) ")
    List<OrderResponse> getOrderByIds(BaseQueryStat baseQueryStat);

    @UpdateProvider(type=OrderMapper.OrderProvider.class,method="updateOrderNo")
    //添加快递单号，修改订单状态
    void updateLogisticsNo(VsjOrder vsjOrder);

    @Select("select product_id from vsj_order where platform_code = #{platformCode} and  member_id = #{memberId} ")
    List<String> getProductIdsByMemberId(Integer memberId);


    @Select("SELECT order_status FROM vsj_order WHERE platform_code = #{platformCode} and order_id = #{orderId}")
    Integer getOrderStatus(Map<String, Object> map);

    @Delete("DELETE vsj_order WHERE platform_code = #{platformCode} and order_id = #{orderId} ")
    void deleteOrder(Map<String, Object> map);


    @Select("IFNULL(0,SUM(o.order_amount_total)) AS money FROM vsj_order o WHERE o.reg_id = #{regId} and " +
            "o.platform_code = #{platformCode} and o.order_type = #{type}")
    Double getMemberConMoney(BaseQueryStat queryStat);
    /**
     * 查询该会员购买指定商品的数量
     * @return int
     */
    @Select("SELECT COUNT(1) AS count FROM vsj_order o WHERE platform_code = #{platformCode} and o.reg_id = #{id} AND FIND_IN_SET(o.product_id, #{ids})")
    int getProCount(BaseQueryStat queryStat);

    @SelectProvider(type = OrderMapper.OrderProvider.class,method = "getOrderByOrderId")
    VsjOrder getOrderByOrderId(@Param("orderId") Integer orderId,@Param("platformCode") Integer platformCode);

    //修改订单详情
    @UpdateProvider(type = OrderMapper.OrderProvider.class,method = "updateOrderAll")
    int updateOrder(OrderResponse orderResponse);

    @SelectProvider(type = OrderMapper.OrderProvider.class,method = "getOrderByOrderNo")
    VsjOrder getOrderByOrderNo(@Param("orderNo") String orderNo, @Param("platformCode") Integer platformCode);

    class OrderProvider{
        public String getOrderList(BaseQueryStat queryStat){
            String sql=new SQL(){
                {
                    SELECT("o.order_id,o.member_id,o.product_name,o.product_id,o.order_no,r.nick_name,o.pay_channel,o.product_amount_total,o.user_remark,o.shop_remark");
                    SELECT("o.order_amount_total,o.create_time,o.pay_time,o.order_status,o.real_name,o.tel_phone,o.site");
                    FROM("vsj_order o");
                    LEFT_OUTER_JOIN("vsj_register r on r.id=o.reg_id");
                    String where = whereBuilder(queryStat);
                    if(StringUtil.isNoEmptyStr(where)){
                        WHERE(where);
                    }
                    ORDER_BY("o.create_time DESC");
                }
            }.toString();
            return sql;
        }
        private String whereBuilder(BaseQueryStat queryStat){
            List<String> list = new ArrayList<>();
            if(queryStat.getId() !=null){
                list.add("o.order_id = #{id}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getTitle())){
                list.add("o.product_name like concat ('%',#{title},'%')");
            }
            if(queryStat.getStatus() != null){
                list.add("o.order_status = #{status}");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getOrderNo())){
                list.add("o.order_no like concat ('%',#{orderNo},'%') ");
            }
            if(StringUtil.isNoEmptyStr(queryStat.getNickName())){
                list.add("r.nick_name like concat ('%',#{nickName},'%') ");
            }
            if (StringUtil.isNoEmptyStr(queryStat.getPayType())){
                list.add("o.pay_channel = #{payType}");
            }
            if(queryStat.getType() != null){
                if(queryStat.getType()==1){
                    if(StringUtil.isNoEmptyStr(queryStat.getStartTime())){
                        list.add("o.create_time <= #{startTime}");
                    }
                    if(StringUtil.isNoEmptyStr(queryStat.getEndTime())){
                        list.add("o.create_time >= #{endTime}");
                    }
                }else if (queryStat.getType()==2){
                    if(StringUtil.isNoEmptyStr(queryStat.getStartTime())){
                        list.add("o.pay_time <= #{startTime}");
                    }
                    if(StringUtil.isNoEmptyStr(queryStat.getEndTime())){
                        list.add("o.pay_time >= #{endTime}");
                    }
                }else if(queryStat.getType()==3){
                    if(StringUtil.isNoEmptyStr(queryStat.getStartTime())){
                        list.add("o.deliver_time <= #{startTime}");
                    }
                    if(StringUtil.isNoEmptyStr(queryStat.getEndTime())){
                        list.add("o.deliver_time >= #{endTime}");
                    }
                }else if (queryStat.getType()==4){
                    if(StringUtil.isNoEmptyStr(queryStat.getStartTime())){
                        list.add("o.complete_time <= #{startTime}");
                    }
                    if(StringUtil.isNoEmptyStr(queryStat.getEndTime())){
                        list.add("o.complete_time >= #{endTime}");
                    }
                }
            }

            list.add("o.platform_code = #{platformCode}");
            return String.join(" and ", list);
        }

        public String updateOrder(BaseQueryStat queryStat){
            String sql = new SQL(){{
                UPDATE("vsj_order");
                if(queryStat.getStatus()!=null){
                    SET("order_status = #{status}");
                }
                if(queryStat.getPrice()!=null){
                    SET("order_amount_total = #{price}");
                }
                if(StringUtil.isNoEmptyStr(queryStat.getLogisticsType())){
                    SET("logistics_type = #{logisticsType}");
                }
                if(StringUtil.isNoEmptyStr(queryStat.getLogisticsNo())){
                    SET("logistics_no = #{logisticsNo}");
                }
                WHERE(" platform_code = #{platformCode} and order_id = #{orderId}");
            }}.toString();
            return sql;
        }
        public String updateOrderNo(VsjOrder vsjOrder){
            String sql = new SQL(){{
                UPDATE("vsj_order");
                    SET("order_status = 2");
                if(StringUtil.isNoEmptyStr(vsjOrder.getLogisticsType())){
                    SET("logistics_type = #{logisticsType}");
                }
                if(StringUtil.isNoEmptyStr(vsjOrder.getLogisticsNo())){
                    SET("logistics_no = #{logisticsNo}");
                }
                WHERE(" platform_code = #{platformCode} and order_no = #{orderNo}");
            }}.toString();
            return sql;
        }

        public String getOrderByOrderId(Integer orderId,Integer platformCode){
            String sql = new SQL(){{
                SELECT("order_id,order_no,reg_id,member_id,shop_id,pay_channel,out_trade_no,order_amount_total");
                SELECT("product_amount_total,pay_time,platform_code");
                FROM("vsj_order");
                WHERE("order_id = #{orderId} AND platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String updateOrderAll(OrderResponse orderResponse){
            String sql = new SQL(){{
                UPDATE("vsj_order");
                if(orderResponse.getOrderStatus()!=null){
                    SET("order_status = #{orderStatus}");
                }
                if(orderResponse.getOrderAmountTotal()!=null){
                    SET("order_amount_total = #{orderAmountTotal}");
                }
                if(orderResponse.getProductAmountTotal()!=null){
                    SET("product_amount_total = #{productAmountTotal}");
                }
                if(StringUtil.isNoEmptyStr(orderResponse.getUserRemark())){
                    SET("user_remark = #{userRemark}");
                }
                if(StringUtil.isNoEmptyStr(orderResponse.getShopRemark())){
                    SET("shop_remark = #{shopRemark}");
                }
                if(StringUtil.isNoEmptyStr(orderResponse.getRealName())){
                    SET("real_name = #{realName}");
                }
                if(StringUtil.isNoEmptyStr(orderResponse.getTelPhone())){
                    SET("tel_phone = #{telPhone}");
                }
                if(StringUtil.isNoEmptyStr(orderResponse.getSite())){
                    SET("site = #{site}");
                }
                WHERE(" platform_code = #{platformCode} and order_id = #{orderId}");
            }}.toString();
            return sql;
        }

        public String getOrderByOrderNo(String orderNo,Integer platformCode){
            String sql = new SQL(){{
                SELECT("order_id,order_no,reg_id,member_id,shop_id,pay_channel,out_trade_no,order_amount_total");
                SELECT("product_amount_total,pay_time,platform_code");
                FROM("vsj_order");
                WHERE("order_no = #{orderNo} AND platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
