package com.vsj.model.response;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


/**
 * @Author: HuaS
 * @Date :2019/7/31 15:13
 * @Describe:订单报表明细出参
 */
@Data
public class ReportCenterResponse {
    private Integer id;
    @Excel(name = "订单id", orderNum = "0",width = 30)
    private Integer orderId;
    @Excel(name = "会员名称", orderNum = "2",width = 30)
    private String nickName;
    @Excel(name = "订单号", orderNum = "3",width = 30)
    private String orderNo;
    @Excel(name = "会员名称", orderNum = "4",width = 30)
    private String levelName;//会员名称
    @Excel(name = "实付金额", orderNum = "5",width = 30)
    private BigDecimal productAmountTotal;//实付金额
    @Excel(name = "订单金额", orderNum = "6",width = 30)
    private BigDecimal orderAmountTotal;//订单金额
    @Excel(name = "订单时间", orderNum = "7",width = 30)
    private String createTime;
    @Excel(name = "分红金额", orderNum = "8",width = 30)
    private BigDecimal operationMoney;//分红金额
    @Excel(name = "分红方式", orderNum = "9",width = 30)
    private Integer incomeSource;//分红方式
    @Excel(name = "结算时间", orderNum = "10",width = 30)
    private String  stockDater;//结算时间
    private Integer sort;//会员等级
    @Builder.Default
    private Integer pageNum = 1;
    @Builder.Default
    private Integer pageSize = 10;
    private Integer platformCode;
    private String startTime;
    private String endTime;

}
