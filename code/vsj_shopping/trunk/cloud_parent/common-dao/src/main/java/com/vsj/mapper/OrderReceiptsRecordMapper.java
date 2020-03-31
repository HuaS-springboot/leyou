package com.vsj.mapper;


import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjOrderReceiptsRecord;
import com.vsj.model.response.ProductSalesReport;
import com.vsj.model.response.ReportCenterResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/7/31 15:10
 * @Describe:订单分成报表明细
 */
@Mapper
public interface OrderReceiptsRecordMapper {

    @SelectProvider(type = OrderReceiptsRecordMapper.ReportCenter.class,method="getOrderReportCenterByName")
    List<ReportCenterResponse> getOrderReportCenterByName(@RequestBody ReportCenterResponse reportCenterResponse);

    @SelectProvider(type = OrderReceiptsRecordMapper.ReportCenter.class,method="getProductSalesReportByName")
    List<ProductSalesReport> getProductSalesReportByName(ProductSalesReport productSalesReport);


    class ReportCenter{
        public String getProductSalesReportByName(ProductSalesReport productSalesReport){
            String sql = new SQL(){{
                SELECT("p.product_name,p.create_time");
                SELECT("ps.sale_num,ps.product_stock,ps.product_price");
                FROM("vsj_product p");
                LEFT_OUTER_JOIN("vsj_product_specs ps ON p.product_id = ps.product_id");
                String where = whereBuilder(productSalesReport);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
            }}.toString();
            return sql;
        }
        private  String whereBuilder(ProductSalesReport productSalesReport){
            List<String> list = new ArrayList<>();
            if(StringUtil.isNoEmptyStr(productSalesReport.getProductName())){
                list.add("p.product_name like concat ('%',#{productName},'%')");
            }
            if(StringUtil.isNoEmptyStr(productSalesReport.getStartTime())){
                list.add(" DATE_FORMAT(p.create_time,'%Y-%m-%d') >= #{startTime}");//p.create_time
            }
            if(StringUtil.isNoEmptyStr(productSalesReport.getEndTime())){
                list.add("DATE_FORMAT(p.create_time,'%Y-%m-%d') <= #{endTime}");//p.create_time
            }
            return String.join(" and ", list);
        }

//        public String getProductSalesReportByName(ReportCenterResponse reportCenterResponse){
//            String sql = new SQL(){{
//                SELECT("o.product_name,o.order_no,o.order_id");
//                SELECT("o.product_amount_total,o.order_amount_total,o.create_time");
//                SELECT("l.level_name");
//                SELECT("orr.operation_money, orr.create_time as stockDater, orr.income_source");
//                FROM("vsj_order_receipts_record orr");
//                LEFT_OUTER_JOIN("vsj_order o ON orr.order_id = o.order_id");
//                LEFT_OUTER_JOIN("vsj_register r ON o.reg_id = r.id");
//                LEFT_OUTER_JOIN("vsj_member m ON r.id = m.reg_id");
//                LEFT_OUTER_JOIN("vsj_member_level l ON m.level_id = l.id");
//                String where = whereBuilder(reportCenterResponse);
//                if(StringUtil.isNoEmptyStr(where)){
//                    WHERE(where);
//                }
//            }}.toString();
//            return sql;
//        }

        public String getOrderReportCenterByName(ReportCenterResponse reportCenterResponse){
            String sql = new SQL(){{
                SELECT("r.id, r.nick_name");
                SELECT("o.order_no,o.order_id,o.product_amount_total,o.order_amount_total,o.create_time");
                SELECT("l.level_name,orr.operation_money, orr.create_time as StockDater, orr.income_source,l.id");
                FROM("vsj_order_receipts_record orr");
                LEFT_OUTER_JOIN("vsj_order o ON orr.order_id = o.order_id");
                LEFT_OUTER_JOIN("vsj_register r ON o.reg_id = r.id");
                LEFT_OUTER_JOIN("vsj_member m ON r.id = m.reg_id");
                LEFT_OUTER_JOIN("vsj_member_level l ON m.level_id = l.id");
                String where = whereBuilder(reportCenterResponse);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
            }}.toString();
            return sql;
        }
        private String whereBuilder(ReportCenterResponse reportCenterResponse){
            List<String> list = new ArrayList<>();
            if(StringUtil.isNoEmptyStr(reportCenterResponse .getNickName())){
                list.add("r.nick_name like concat ('%',#{nickName},'%')");
            }
//            if(StringUtil.isNoEmptyStr(reportCenterResponse.getLevelName())){
//                list.add("l.level_name = #{levelName}");
//            }
            if(reportCenterResponse.getId()!=null){
                list.add("l.id = #{id}");
            }
            if(StringUtil.isNoEmptyStr(reportCenterResponse.getOrderNo())){
                list.add("o.order_no = #{orderNo}");
            }
            if(StringUtil.isNoEmptyStr(reportCenterResponse.getStartTime())){
                list.add("o.create_time >= DATE_FORMAT(#{startTime},'%Y-%m-%d')");
            }
            if(StringUtil.isNoEmptyStr(reportCenterResponse.getEndTime())){   //queryStat.getEndTime() != null
                list.add("o.create_time <= DATE_FORMAT(#{endTime},'%Y-%m-%d')");
            }
            return String.join(" and ", list);
        }


    }

}
