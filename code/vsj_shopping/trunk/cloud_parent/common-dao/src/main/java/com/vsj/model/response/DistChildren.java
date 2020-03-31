package com.vsj.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DistChildren
 * @Description TODO
 * @Date 2019/8/20 13:31
 * @Created by wangzx
 */
@Data
public class DistChildren implements Serializable {
    private List<CityDist> cityDist = new ArrayList<>();
    private Integer firstHeavy;
    private Integer firstHeavyCost;
    private Integer model;
    private Integer nextHeavy;
    private Integer nextHeavyCost;
    private String provinceCode;
    private String name;
}
