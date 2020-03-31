package com.vsj.model.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname SysReviewResponse
 * @Description 审核列表返回示例
 * @Date 2019/8/21 15:20
 * @Created by wangzx
 */
@Data
public class SysReviewResponse implements Serializable {

    private static final long serialVersionUID = -3591953197724709582L;

    private Integer id;
    private Integer regId;
    private String nickName;
    private String levelName;
    private Integer levelId;
    private BigDecimal withdrawAmount;
    private Integer status;
    private String createTime;
    private String finishTime;
    private Integer platformCode;
    private String openId;
    private String paymentNo;
}
