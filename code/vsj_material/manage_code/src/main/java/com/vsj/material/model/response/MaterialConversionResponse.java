package com.vsj.material.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname MaterialConversionResponse
 * @Description 兑换码列表返回实体类
 * @Date 2019/8/16 11:22
 * @Created by wangzx
 */
@Data
public class MaterialConversionResponse implements Serializable {

    private static final long serialVersionUID = -2091511416495035300L;

    private Integer id;
    private String code;
    private Integer levelId;
    private Integer levelDay;
    private String effectiveTime;
    private String invalidTime;
    private Integer status;
    private String levelName;
    private String nickName;
    private String useTime;
    private String telPhone;
}
