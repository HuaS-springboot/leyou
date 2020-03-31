package com.vsj.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BaseQueryStat {
    private Integer id;
    private String ids;
    private Integer orderId;
    private Integer type;
    private Integer status;
    private String startTime;
    private String endTime;
    private String title;
    private Integer oneCateId;
    private Integer twoCateId;
    private Integer threeCateId;
    private Integer priceMax;
    private Integer priceMin;
    private String headUrl;
    private String telPhone;//手机号
    private String productName;//商品名
    private String createTime;//订单时间
    private Integer sort;//会员等级
    private Integer levelId;//会员等级Id
    private Integer parentId;//推荐人Id
    private String parentName;//推荐人Id
    private String  nickName;//用户名
    private String  orderNo;//订单号
    private String payType;//支付方式
    private Integer categoryStatus;//产品分类状态
    private Integer platformCode;
    private Integer regId;// 被邀请人id
    private Integer referrerId;//上级邀请人id
    private Integer count;
    private Integer productId;
    private Integer oldId;
    private Double price;
    private String remark;
    private String logisticsType;
    private String logisticsNo;
    private Integer beforeStatus;
    private Integer sysUserId;
    private Integer memberId;
    private String fuzzyList;//账号/姓名/手机号
    private String ip;
    private BigDecimal money;
}
