package com.vsj.model.response;

import com.vsj.model.VsjProductSpecs;
import lombok.Data;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTParaRPrImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProductResponse
 * @Description 商品相关返回参数
 * @Date 2019/7/23 17:24
 * @Created by wangzx
 */
@Data
public class ProductResponse {

    private Integer productId;
    private Integer saleNum;
    private Integer productSort;
    private Integer publishStatus;
    private Double price;
    private String productName;
    private String productImage;
    private Integer openPeers;
    private Integer bonusNum;
    private Integer bonusUnits;
    private Integer peersNum;
    private Integer peersHierarchy;
    private Integer perrsUnits;
    private Integer isDealer;
    private Integer isCommission;
    private Integer templateId;
    private Integer oneCategoryId;
    private Integer twoCategoryId;
    private Integer threeCategoryId;
    private Integer isExchange;
    private Integer weight;
    private String saleTime;
    private String shareImage;
    private String shareTitle;
    private String describesion;
    private List<VsjProductSpecs> specsList = new ArrayList<>();

}
