package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname MemberPackageRequestList
 * @Description 会员套餐参数修改
 * @Date 2019/8/23 11:23
 * @Created by wd
 */
@Data
public class MemberPackageRequestList implements Serializable {
    private Integer status;
    private List<MaterialMemberPackageRequest> memberPackageList = new ArrayList<>();
}
