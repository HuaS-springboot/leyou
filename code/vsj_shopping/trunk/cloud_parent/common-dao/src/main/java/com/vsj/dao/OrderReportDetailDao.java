package com.vsj.dao;

import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.mapper.OrderReceiptsRecordMapper;
import com.vsj.model.VsjOrderReceiptsRecord;
import com.vsj.model.response.ProductSalesReport;
import com.vsj.model.response.ReportCenterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderReportDetailDao {
    @Autowired
    private OrderReceiptsRecordMapper orderReceiptsRecordMapper;
    @Autowired
    private RedisClient redisClient;

    public void deleteProductCateKey() {
        redisClient.remove(RedisKeyConstant.PRODUCT_CATEGORY_KEY);
    }

    public List<ReportCenterResponse> getOrderReportCenterByName(ReportCenterResponse reportCenterResponse){
        return orderReceiptsRecordMapper.getOrderReportCenterByName(reportCenterResponse);
    }

    public List<ProductSalesReport> getProductSalesReportByName(ProductSalesReport productSalesReport){
        return orderReceiptsRecordMapper.getProductSalesReportByName(productSalesReport);
    }

}
