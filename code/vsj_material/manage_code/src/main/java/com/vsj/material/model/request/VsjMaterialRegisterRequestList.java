package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjMaterialRegisterRequestList
 * @Description 手机端用户信息集合
 * @Date 2019/8/16 9:43
 * @Created by sxm
 */
@Data
public class VsjMaterialRegisterRequestList implements Serializable {
    private static final long serialVersionUID = -5282122713708977978L;
    private List<VsjMaterialRegisterRequest> list = new ArrayList<>();
}
