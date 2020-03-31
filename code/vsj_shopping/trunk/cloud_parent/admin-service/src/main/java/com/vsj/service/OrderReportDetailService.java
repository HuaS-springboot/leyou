package com.vsj.service;


import com.vsj.model.response.ProductSalesReport;
import com.vsj.model.response.ReportCenterResponse;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

public interface OrderReportDetailService {

    Object getOrderReportCenterByName(@RequestBody ReportCenterResponse reportCenterResponse);

    Object getProductSalesReportByName(ProductSalesReport productSalesReport);

    boolean exportOrderReportListExcel(ReportCenterResponse reportCenterResponse, HttpServletResponse response);

    boolean exportProductSalesReportListExcel(ProductSalesReport productSalesReport,HttpServletResponse response);
}
