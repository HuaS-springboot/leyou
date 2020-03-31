package com.vsj.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.utils.ExcelUtils;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.OrderReportDetailDao;
import com.vsj.model.response.MemberResponse;
import com.vsj.model.response.ProductSalesReport;
import com.vsj.model.response.ReportCenterResponse;
import com.vsj.service.OrderReportDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/7/31 16:07
 * @Describe:
 */
@Service
public class OrderReportDetailServiceImpl implements OrderReportDetailService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderReportDetailDao orderReportDetailDao;


    @Override
    public Object getOrderReportCenterByName(@RequestBody ReportCenterResponse reportCenterResponse) {
        PageHelper.startPage(reportCenterResponse.getPageNum(),reportCenterResponse.getPageSize());
        List<ReportCenterResponse> reportCenterResponseList = orderReportDetailDao.getOrderReportCenterByName(reportCenterResponse);
        PageInfo<ReportCenterResponse> info = new PageInfo<>(reportCenterResponseList);
        return BaseResponseParam.success(info);
    }

    @Override
    public Object getProductSalesReportByName(ProductSalesReport productSalesReport) {
        PageHelper.startPage(productSalesReport.getPageNum(),productSalesReport.getPageSize());
        List<ProductSalesReport> productSalesReportList = orderReportDetailDao.getProductSalesReportByName(productSalesReport);
        PageInfo<ProductSalesReport> info = new PageInfo<>(productSalesReportList);
        return BaseResponseParam.success(info);
    }

    @Override
    public boolean exportOrderReportListExcel(ReportCenterResponse reportCenterResponse, HttpServletResponse response) {
        List<ReportCenterResponse> reportCenterResponseList = orderReportDetailDao.getOrderReportCenterByName(reportCenterResponse);
        if(StringUtil.isEmptyList(reportCenterResponseList)){
            logger.debug("根据条件={},查询到列表为空,无需导出...", JSON.toJSONString(reportCenterResponseList));
            return false;
        }
        ExcelUtils.exportExcel(reportCenterResponseList,"订单分成明细报表","订单分成明细报表",ReportCenterResponse.class,"订单分成明细报表.xls",response);
        return true;
    }

    @Override
    public boolean exportProductSalesReportListExcel(ProductSalesReport productSalesReport, HttpServletResponse response) {
        List<ProductSalesReport> productSalesReportList = orderReportDetailDao.getProductSalesReportByName(productSalesReport);
        if(StringUtil.isEmptyList(productSalesReportList)){
            logger.debug("根据条件={},查询到列表为空,无需导出...",JSON.toJSONString(productSalesReportList));
            return false;
        }
        ExcelUtils.exportExcel(productSalesReportList,"产品销量统计报表","产品销量统计报表",ProductSalesReport.class,"产品销量统计报表.xls",response);
        return false;
    }


}
