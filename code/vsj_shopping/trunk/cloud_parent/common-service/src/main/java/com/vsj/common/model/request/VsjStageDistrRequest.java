package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjStageDistrRequest
 * @Description 分销模式参数接受实体类
 * @Date 2019/8/14 13:55
 * @Created by wangzx
 */
@Data
public class VsjStageDistrRequest implements Serializable {

    private static final long serialVersionUID = -8434657877370174089L;

    private Integer id;
    private Integer level;
    private Integer firstLevelModel;
    private Integer firstLevelValue;
    private Integer secondLevelModel;
    private Integer secondLevelValue;
    private Integer productId;
    private Integer isDealer;
    private Integer isCommission;
}
