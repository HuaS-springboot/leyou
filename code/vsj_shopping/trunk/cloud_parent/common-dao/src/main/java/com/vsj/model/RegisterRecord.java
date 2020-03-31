package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname RegisterRecord
 * @Description 会员流水明细MODEL
 * @Date 2019/8/26 16:46
 * @Created by wangzx
 */
@Data
public class RegisterRecord implements Serializable {

    private static final long serialVersionUID = 2936954489240275504L;
    private Integer id;
    private String createTime;
    private Integer regId;
    private BigDecimal carryBalance;
    private BigDecimal totalBalance;
    private Integer type;
    private String source;
    private Integer platformCode;
}
