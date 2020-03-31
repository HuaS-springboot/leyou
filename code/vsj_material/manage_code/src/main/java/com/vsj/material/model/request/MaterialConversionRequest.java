package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname MaterialConversionRequest
 * @Description 兑换码参数接受类
 * @Date 2019/8/16 9:54
 * @Created by wangzx
 */
@Data
public class MaterialConversionRequest implements Serializable {

    private static final long serialVersionUID = -2229727644914624866L;
    private Integer levelId;
    private String effectiveTime;
    private String invalidTime;
    private Integer levelDay;
    private Integer count;
}
