package com.vsj.model.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MemberTeamResponse
 * @Description 会员团队返回参数
 * @Date 2019/8/26 10:30
 * @Created by wangzx
 */
@Data
public class MemberTeamResponse implements Serializable {

    private static final long serialVersionUID = -3546863385589233085L;

    private Integer id;

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

    private String headUrl;

    private String telPhone;

    private Integer parentId;

    private String parentName;

    private String joinTime;

    private String nickName;

    private List<MemberTeamResponse> children = new ArrayList<>();
}
