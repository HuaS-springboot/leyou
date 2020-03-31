package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: HuaS
 * @Date :2019/8/2 10:26
 * @Describe:
 */
@Data
public class MemberLevel implements Serializable {
    private Integer id;
    private Integer oldId;
    private String levelName;
    private Integer sort;
    private Integer isDefault;
    private Integer platformCode;
}
