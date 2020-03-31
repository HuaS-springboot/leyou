package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjSysAreas
 * @Description TODO
 * @Date 2019/7/25 15:21
 * @Created by wangzx
 */
@Data
public class VsjSysAreas implements Serializable {
    private static final long serialVersionUID = -8187911859292497047L;
    private Integer id;
    private String code;
    private String parentCode;
    private String name;
    private String province;
    private String city;
    private String district;
    private String fullName;
    private String grade;
    private Integer status;// 是否有配送模板 0=没有；1=有
    private List<VsjSysAreas> children = new ArrayList<>();
}
