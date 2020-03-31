package com.vsj.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname CityConfig
 * @Description TODO
 * @Date 2019/8/20 13:35
 * @Created by wangzx
 */
@Data
public class CityDist implements Serializable {
    private String province;
    private List<String> cityCode = new ArrayList<>();
}
