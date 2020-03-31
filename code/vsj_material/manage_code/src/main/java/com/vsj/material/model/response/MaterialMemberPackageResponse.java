package com.vsj.material.model.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Classname MaterialMemberPackageResponse
 * @Description 会员套餐包
 * @Date 2019/8/25 18:54
 * @Created by wd
 */
@Data
public class MaterialMemberPackageResponse {
    private Integer id;
    private Integer status;
    private Integer levelId;
    private Integer day;
    private BigDecimal money;
}
