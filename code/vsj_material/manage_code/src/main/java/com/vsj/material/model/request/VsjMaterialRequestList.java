package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjMaterialRequestList
 * @Description 素材管理信息集合
 * @Date 2019/8/13 14:20
 * @Created by sxm
 */
@Data
public class VsjMaterialRequestList implements Serializable {
    private static final long serialVersionUID = -1314185436012611032L;
    private List<VsjMaterialRequest> vsjMaterialRequestList = new ArrayList<>();
}
