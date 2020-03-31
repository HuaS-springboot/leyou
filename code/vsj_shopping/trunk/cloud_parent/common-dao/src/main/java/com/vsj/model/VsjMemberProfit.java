package com.vsj.model;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class VsjMemberProfit implements Serializable {

    private static final long serialVersionUID = 5346540707585222201L;

    private Integer id;

    private Integer memberId;

    private Integer regId;

    private Integer firstLowerCount;

    private Integer secondLowerCount;

    private Integer lowerLevelCount;

    private BigDecimal firstTeamResults;

    private BigDecimal secondTeamResults;

    private BigDecimal allTeamResults;

    private Integer completOrder;

    private BigDecimal completOrderResults;

    private BigDecimal settledWages;

    private BigDecimal noSettledWages;

    private BigDecimal totalWages;
    
    private Integer platformCode;

}