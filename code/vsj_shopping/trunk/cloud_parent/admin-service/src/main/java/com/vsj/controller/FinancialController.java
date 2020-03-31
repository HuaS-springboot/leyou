package com.vsj.controller;


import com.alibaba.fastjson.JSON;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.response.ProductSalesReport;
import com.vsj.model.response.ReportCenterResponse;
import com.vsj.service.OrderReportDetailService;
import com.vsj.service.SysReviewService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.lang.management.PlatformLoggingMXBean;
import java.math.BigDecimal;

/**
 * @Author: HuaS
 * @Date :2019/7/31 15:07
 * @Describe:财务中心
 */
@RestController
@RequestMapping(value = "api/v1/financial/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class FinancialController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderReportDetailService orderReportDetailService;
    @Autowired
    private SysReviewService sysReviewService;

    @ApiOperation(value = "查询订单明细", notes = "查询订单明细", httpMethod = "POST")
    @PostMapping("getOrderReportCenterByName")
    public Object getOrderReportCenterByName(@RequestBody ReportCenterResponse reportCenterResponse){
        logger.info("查询订单明细getOrderReportCenter入参:"+reportCenterResponse);
        Object pageInfo = orderReportDetailService.getOrderReportCenterByName(reportCenterResponse);
        logger.info("查询订单明细getOrderReportCenter入参:"+pageInfo);
        return pageInfo;
    }

    @ApiOperation(value = "查询商品销量明细", notes = "查询商品销量明细", httpMethod = "POST")
    @PostMapping("getProductSalesReportByName")
    public Object getProductSalesReportByName(@RequestBody ProductSalesReport productSalesReport){
        logger.info("查询商品销量明细getProductSalesReportByName入参:"+productSalesReport);
        Object pageInfo = orderReportDetailService.getProductSalesReportByName(productSalesReport);
        logger.info("查询商品销量明细getProductSalesReportByName入参:"+pageInfo);
        return pageInfo;
    }

    @ApiOperation(value = "订单分成明细报表导出", notes = "订单分成明细报表导出", httpMethod = "POST")
    @RequestMapping("exportOrderReportListExcel")
    public Object exportOrderReportListExcel(String nickName, String orderNo, String levelName, BigDecimal orderAmountTotal, BigDecimal productAmountTotal,
                                             String createTime, BigDecimal operationMoney, Integer incomeSource, String StockDater, HttpServletResponse response){
        logger.info("开始处理订单分成明细报表导出...");
        long start = System.currentTimeMillis();
        ReportCenterResponse reportCenterResponse = new ReportCenterResponse();
        reportCenterResponse.setNickName(nickName);
        reportCenterResponse.setOrderNo(orderNo);
        reportCenterResponse.setLevelName(levelName);
        reportCenterResponse.setOrderAmountTotal(orderAmountTotal);
        reportCenterResponse.setProductAmountTotal(productAmountTotal);
        reportCenterResponse.setCreateTime(createTime);
        reportCenterResponse.setOperationMoney(operationMoney);
        reportCenterResponse.setIncomeSource(incomeSource);
        reportCenterResponse.setStockDater(StockDater);
        logger.debug("请求参数={}", JSON.toJSONString(reportCenterResponse));
        boolean flag = orderReportDetailService.exportOrderReportListExcel(reportCenterResponse,response);
        logger.info("会员列表导出完成...耗时={}", (System.currentTimeMillis() - start));
        if(flag){
            return BaseResponseParam.success();
        }else{
            return BaseResponseParam.fail("查询到的导出数据为空...");
        }
    }

    @ApiOperation(value = "产品销量统计报表导出", notes = "产品销量统计报表导出", httpMethod = "POST")
    @RequestMapping("exportProductSalesReportListExcel")
    public Object exportProductSalesReportListExcel(String productName,Integer saleNum,Integer productStock,String createTime, BigDecimal productPrice,  HttpServletResponse response){
        logger.info("开始处理产品销量统计报表导出......");
        long start =  System.currentTimeMillis();
        ProductSalesReport productSalesReport = new ProductSalesReport();
        productSalesReport.setCreateTime(createTime);
        productSalesReport.setProductName(productName);
        productSalesReport.setSaleNum(saleNum);
        productSalesReport.setProductStock(productStock);
        productSalesReport.setProductPrice(productPrice);
        logger.debug("请求参数={}",JSON.toJSONString(productSalesReport));
        boolean flag = orderReportDetailService.exportProductSalesReportListExcel(productSalesReport,response);
        logger.info("产品销量统计报表导出额按成...耗时={}",(System.currentTimeMillis()-start));
        if(flag){
            return BaseResponseParam.success();
        }else{
            return BaseResponseParam.fail("查询到的导出数据为空");
        }

    }

    /**
     * @Description 查询审核列表
     * @Author  wangzx
     * @Date   2019/8/21 15:02
     * @Param  [queryStat]
     * type:1=提现审核
     * nickName:注册会员昵称
     * levelId:等级id
     * startTime:提现申请开始时间
     * endTime:提现申请结束时间
     * status:审核状态 提现申请状态 0=待审核；1=审核通过；2=审核未通过
     * @Return com.vsj.common.model.BaseResponseParam
     * @Exception
     */
    @PostMapping("/getReviewList")
    public BaseResponseParam getReviewList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询审核列表getReviewList入参={}",queryStat);
        BaseResponseParam responseParam = sysReviewService.getReviewList(queryStat);
        logger.info("查询审核列表getReviewList出参={}",responseParam);
        return responseParam;
    }

    /**
     * @Description 提现审核
     * @Author  wangzx
     * @Date   2019/8/21 16:37
     * @Param  [queryStat]
     * id:审核id
     * status:1=审核通过 已付款；2=审核未通过
     * openId:会员openId
     * @Return com.vsj.common.model.BaseResponseParam
     * @Exception
     */
    @PostMapping("/review")
    public BaseResponseParam updateReview(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("提现审核review入参={}",queryStat);
        BaseResponseParam responseParam = sysReviewService.updateReview(queryStat,request);
        logger.info("提现审核review出参={}",responseParam);
        return responseParam;
    }

}
