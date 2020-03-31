package com.vsj.model.response;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSalesReport {
    @Excel(name = "产品名称", orderNum = "0",width = 30)
    private String productName;
    @Excel(name = "创建时间", orderNum = "1",width = 30)
    private String createTime;
    @Excel(name = "销量", orderNum = "2",width = 30)
    private Integer saleNum;
    @Excel(name = "库存", orderNum = "3",width = 30)
    private Integer productStock;
    @Excel(name = "售价", orderNum = "4",width = 30)
    private BigDecimal productPrice;
    @Builder.Default
    private Integer pageNum = 1;
    @Builder.Default
    private Integer pageSize = 10;
    private Integer platformCode;
    private String startTime;
    private String endTime;

}
