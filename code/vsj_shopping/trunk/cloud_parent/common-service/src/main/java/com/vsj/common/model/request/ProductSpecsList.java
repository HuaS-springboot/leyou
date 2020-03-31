package com.vsj.common.model.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProductSpecsList
 * @Description TODO
 * @Date 2019/7/26 14:06
 * @Created by wangzx
 */
@Data
public class ProductSpecsList {

    private Integer platformCode;

    private List<VsjProductSpecsRequest> specsList = new ArrayList<>();
}
