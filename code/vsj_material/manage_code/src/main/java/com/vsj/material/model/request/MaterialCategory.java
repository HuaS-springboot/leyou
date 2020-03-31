package com.vsj.material.model.request;

import com.vsj.material.model.VsjMaterialCategory;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MaterialCategory {
    private Integer id;
    private String cateName;
    private Integer parentId;
    private Integer sort;
    private Integer status;
    private String createTime;
    private Integer platformCode;
    private List<VsjMaterialCategory> children = new ArrayList<>();
}
