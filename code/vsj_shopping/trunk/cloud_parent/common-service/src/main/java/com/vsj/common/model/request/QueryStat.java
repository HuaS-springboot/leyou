package com.vsj.common.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Classname QueryStat
 * @Description 公共查询类
 * @Date 2019/7/26 12:00
 * @Created by wangzx
 */
@Data
@NoArgsConstructor
public class QueryStat {
	private Integer id;
	private Integer userId;
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
    @Builder.Default
    private Integer pageNum = 1;
    @Builder.Default
    private Integer pageSize = 10;
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
    private Integer tempId;// 配送模板id
    private String cityCode;// 购买人地址的市级code码
    private String openId;// 会员openId
    private String code;//小程序code
    private String ip;
    private BigDecimal money;
}
