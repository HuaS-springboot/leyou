package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjOrder;
import com.vsj.model.VsjOrderDetail;
import com.vsj.model.VsjProductSpecs;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @Classname CreateOrderMapper
 * @Description TODO
 * @Date 2019/8/2 10:41
 * @Created by zy
 */
@Mapper
public interface CreateOrderMapper {

    @Select("SELECT product_stock,isnull_sell FROM vsj_product_specs WHERE  id =#{specsId}")
    VsjProductSpecs getOrderIsNullSell(Integer specsId);

    @Select("SELECT count(*) FROM vsj_order WHERE   reg_id = #{regId} AND order_status=0")
    int getUnpaidOrderNumber(Integer regId);

    @InsertProvider(type = com.vsj.mapper.CreateOrderMapper.CreateOrderClass.class,method = "insertOrder")
    @Options(useGeneratedKeys=true,keyProperty="orderId")
    int insertOrder(VsjOrder order);

    @Insert(" \"<script>\",\n" +
            "\"insert into vsj_order_detail(order_id, specs_id, product_price,params_json,number,subtotal,create_time,remark,attr_json,platform_code) values \",\n" +
            "\"<foreach collection='orderDetail' item='item' index='index' separator=','>\",\n" +
            "\"(#{item.orderId}, #{item.specsId}, #{item.productPrice},#{item.paramsJson},#{item.number},#{item.subtotal},NOW(),#{item.remark},#{item.attrJson},#{item.platformCode})\",\n" +
            "\"</foreach>\",\n" +
            "\"</script>\"")
    int insertOrderDetail(List<VsjOrderDetail> orderDetail);

    @Select("SELECT * FROM vsj_order WHERE order_no = #{orderNo}")
    VsjOrder getOrderByOrderNo(String orderNo);

    //@Update("update vsj_order set order_status=1,out_trade_no=#{transaction_id},pay_time=NOW(),update_time=NOW()  where order_no = #{out_trade_no}")
    @UpdateProvider(type =CreateOrderMapper.CreateOrderClass.class,method = "updateOrderStatus")
    int updateOrderStatus(@Param("out_trade_no") String out_trade_no,@Param("transaction_id")String transaction_id);

    class CreateOrderClass{
        public String insertOrder(VsjOrder order){
            String sql = new SQL(){{
                INSERT_INTO("vsj_order");
                if(StringUtil.isNoEmptyStr(order.getOrderNo())){
                    VALUES("order_no","#{orderNo}");
                }
                if(order.getRegId() != null){
                    VALUES("reg_id","#{regId}");
                }
                if(order.getProductId() != null){
                    VALUES("product_id","#{productId}");
                }
                if(StringUtil.isNoEmptyStr(order.getProductName())){
                    VALUES("product_name","#{productName}");
                }
                if(order.getProductCount() != null){
                    VALUES("product_count","#{productCount}");
                }
                VALUES("order_status","0");
                if(order.getProductAmountTotal() != null){
                    VALUES("product_amount_total","#{productAmountTotal}");
                }
                if(order.getOrderAmountTotal() != null){
                    VALUES("order_amount_total","#{orderAmountTotal}");
                }
                if(order.getLogisticsFee() != null){
                    VALUES("logistics_fee","#{logisticsFee}");
                }
                if(order.getAddressId() != null){
                    VALUES("address_id","#{addressId}");
                }
                if(order.getOrderType() != null){
                    VALUES("order_type","#{orderType}");
                }
                if(order.getDividendOrder() != null){
                    VALUES("dividend_order","#{dividendOrder}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }
        public String updateOrderStatus(@Param("out_trade_no") String out_trade_no,@Param("transaction_id")String transaction_id){
            String sql = new SQL(){{
                UPDATE("vsj_order");
                if(StringUtil.isNoEmptyStr(transaction_id)){
                    SET("out_trade_no = #{transaction_id}" );
                }
                    SET("order_status=1" );
                    SET("pay_time=NOW()" );
                    SET("update_time=NOW()" );
                WHERE("order_no = #{out_trade_no}");
            }}.toString();
            return sql;
        }
    }
}
