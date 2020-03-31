package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProductImgRequestList
 * @Description TODO
 * @Date 2019/8/23 17:00
 * @Created by wangzx
 */
@Data
public class ProductImgRequestList implements Serializable {

    private Integer type;

    private List<VsjProductImgRequest> imgRequestList = new ArrayList<>();
}
