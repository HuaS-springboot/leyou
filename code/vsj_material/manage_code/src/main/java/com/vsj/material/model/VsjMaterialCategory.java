package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjMaterialCategory
 * @Description 分类model
 * @Date 2019/8/13 17:08
 * @Created by wangzx
 */
@Data
public class VsjMaterialCategory implements Serializable {

    private static final long serialVersionUID = -7553822010775236893L;

    private Integer id;
    private String cateName;
    private Integer parentId;
    private Integer sort;
    private String createTime;
    private Integer platformCode;
    private List<VsjMaterialCategory> children = new ArrayList<>();
}
