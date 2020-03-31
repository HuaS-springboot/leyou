package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjProductExtensionRequestList
 * @Description 扩展属性集合
 * @Date 2019/8/13 16:28
 * @Created by wangzx
 */
@Data
public class VsjProductExtensionRequestList implements Serializable {

    private List<VsjProductExtensionRequest> productExtensionList = new ArrayList<>();
}
