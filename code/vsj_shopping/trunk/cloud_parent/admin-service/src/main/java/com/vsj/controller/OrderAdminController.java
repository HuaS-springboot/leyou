package com.vsj.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONObject;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.utils.ExcelUtils;
import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjOrder;
import com.vsj.model.VsjOrderOperation;
import com.vsj.model.response.OrderResponse;
import com.vsj.service.OrderOperationService;
import com.vsj.service.OrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname OrderAdminController
 * @Description 订单管理相关接口
 * @Date 2019/7/24 16:14
 * @Created by zy
 */
@RestController
@RequestMapping(value="api/v1/order/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class OrderAdminController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderOperationService orderOperationService;

    /**
     * @Description
     * @Author zy
     * @Date   2019/7/24 17:34
     * @Param  [queryStat]
     * orderNo:订单号
     * nickName:用户名
     * title:商品名
     * type:订单类型
     * payType:支付方式
     * startTime:下单时间--起值
     * endTime:下单时间--终值
     * status:订单状态
     * @Return      java.lang.Object
     * @Exception
     *
     */

    @ApiOperation(value="查询订单列表",notes = "查询订单列表",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderNo",value="订单号",dataType = "String"),
            @ApiImplicitParam(name="nickName",value="账户名",dataType = "String"),
            @ApiImplicitParam(name="title",value="商品名",dataType = "String"),
            @ApiImplicitParam(name="type",value="时间类型",dataType = "Integer"),
            @ApiImplicitParam(name="payType",value="支付方式",dataType = "String"),
            @ApiImplicitParam(name="startTime",value="下单时间--起值",dataType = "String"),
            @ApiImplicitParam(name="endTime",value="下单时间--终值",dataType = "String"),
            @ApiImplicitParam(name="status",value="订单状态",dataType = "Integer")
    })
    @RequestMapping("getOrderList")
    public  Object getOrderList(@RequestBody QueryStat queryStat){
        logger.info("查询订单列表getOrderList入参:"+queryStat);
        Object pageInfo = orderService.getOrderList(queryStat);
        logger.info("查询订单列表getOrderList出参:"+pageInfo);
        return pageInfo;
    }

    @ApiOperation(value="查询订单详情接口",notes = "查询订单详情接口",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="订单Id",dataType = "Integer")
    })
    @RequestMapping("getOrderDetailsList")
    public Object getOrderDetailsList(@RequestBody QueryStat queryStat){
        logger.info("查询订单详情列表getOrderDetailsList入参:"+queryStat);
        if(queryStat.getId() == null){
            return BaseResponse.fail("参数错误");
        }
        Object pageInfo = orderService.getOrderDetailsList(queryStat);
        logger.info("查询订单详情列表getOrderDetailsList出参:"+pageInfo);
        return pageInfo;
    }
    @ApiOperation(value="修改订单状态接口",notes = "修改订单状态接口",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderId",value="订单ID",dataType = "Integer"),
            @ApiImplicitParam(name="remark",value="备注",dataType = "String"),
            @ApiImplicitParam(name="price",value="价格",dataType = "Double"),
            @ApiImplicitParam(name="logisticsType",value="快递类型",dataType = "String"),
            @ApiImplicitParam(name="logisticsNo",value="快递单号",dataType = "String"),
            @ApiImplicitParam(name="status",value="订单状态",dataType = "Integer"),
            @ApiImplicitParam(name="beforeStatus",value="订单修改之前的状态",dataType = "Integer"),
            @ApiImplicitParam(name="type",value="退款审核通过/不通过 0=不通过;1=通过",dataType = "Integer"),
            @ApiImplicitParam(name="sysUserId",value="后台用户id",dataType = "Integer"),
            @ApiImplicitParam(name="memberId",value="当前的登录人id",dataType = "Integer")
    })
    @RequestMapping("updateOrderStatus")
    public Object updateOrderStatus( @RequestBody QueryStat queryStat){

        return orderService.updateOrderStatus(queryStat);
    }
    @ApiOperation(value="批量发货模板下载接口",notes = "批量发货模板",httpMethod = "POST")
    @RequestMapping("exportExcel")
    public Object export(HttpServletResponse response) {

        List<VsjOrder> list=new ArrayList<>();
        try {
        ExcelUtils.exportExcel(list, "批量发货模板", "批量发货模板", VsjOrder.class, "批量发货模板.xls", response);
        } catch (Exception e) {
            BaseResponse.fail("下载失败");
        }
        return BaseResponse.success();
    }
    @ApiOperation(value="订单列表导出接口",notes = "订单导出接口",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="ids",value="订单ID字符串",dataType = "String")
    })
    @RequestMapping("exportOrderExcel")
    public Object export(HttpServletResponse response,String ids) {
        if(StringUtil.isEmptyStr(ids)){
            return BaseResponse.fail("参数错误");
        }
        QueryStat queryStat = new QueryStat();
        queryStat.setIds(ids);
        queryStat.setPlatformCode(getPlatformCode());
        List<OrderResponse> list=orderService.getOrderByIds(queryStat);
        ExcelUtils.exportExcel(list, "订单列表", "订单列表", OrderResponse.class, "订单列表.xls", response);
        return BaseResponse.success();
    }
    @ApiOperation(value="批量发货模板导入接口",notes = "批量发货模板导入接口",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="file",value="批量发货模板",dataType = "MultipartFile"),
            @ApiImplicitParam(name="logisticsType",value="快递类型",dataType = "String")
    })
    @RequestMapping("importExcel")
    public Object importExce(@RequestParam("file") MultipartFile file, String logisticsType) {
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        // 需要验证
        importParams.setNeedVerfiy(false);
        if (StringUtil.isEmptyStr(logisticsType)){
            BaseResponse.fail("必要参数丢失");
        }
        try {
            ExcelImportResult<VsjOrder> result = ExcelImportUtil.importExcelMore(file.getInputStream(), VsjOrder.class, importParams);
            List<VsjOrder> userList = result.getList();
            for (VsjOrder vsjOrder : userList) {
                // System.out.println(User);
                vsjOrder.setLogisticsType(logisticsType);
                logger.info("从Excel导入数据到数据库的详细为 ：{}", JSONObject.toJSONString(vsjOrder));
                //TODO 将导入的数据做保存数据库操作
                vsjOrder.setPlatformCode(getPlatformCode());
                orderService.updateLogisticsNo(vsjOrder);
            }
            logger.info("从Excel导入数据一共 {} 行 ", userList.size());
        } catch (IOException e) {
            BaseResponse.fail("导入失败：{}");
        } catch (Exception e1) {
            BaseResponse.fail("导入失败：{}");
        }
        return BaseResponse.success();
    }


    @ApiOperation(value = "查询订单操作",notes = "查询订单操作",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderId",value="订单id",dataType = "Integer")
    })
    @RequestMapping("selectOrderOperation")
    public Object selectOrderOperation(@RequestBody VsjOrderOperation vsjOrderOperation){
        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        vsjOrderOperation.setPlatformCode(getPlatformCode());
        logger.info("查询订单操作selectOrderOperation入参:"+vsjOrderOperation);
        Object info = orderOperationService.selectOrderOperation(vsjOrderOperation);
        logger.info("查询订单操作selectOrderOperation出参:"+info);
        return info;
    }
    /**
     * @Description 修改订单详情信息
     * @Author zy
     * @Date   2019/8/20 9:45
     * @Param  [orderResponse]
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @ApiOperation(value = "修改订单信息", notes = "修改订单信息", httpMethod = "POST")
    @RequestMapping("updateOrder")
    public BaseResponseParam updateOrder(@RequestBody OrderResponse orderResponse){

        if(null == getPlatformCode()){
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        orderResponse.setPlatformCode(getPlatformCode());
        logger.info("修改订单信息入参:"+orderResponse);
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=orderService.updateOrder(orderResponse);
        logger.info("查询系统用户列表请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    /**
     * @Description 微信退款支付回调
     * @Author  wangzx
     * @Date   2019/8/20 16:26
     * @Param  [request, response]
     * @Return      java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "微信支付回调", notes = "微信支付回调", httpMethod = "POST")
    @PostMapping("/wxRefundOrderNotify")
    public void wxpayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        orderService.wxRefundOrderNotify(request,response,getPlatformCode());
    }

}
