package com.vsj.common.model.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjProductList
 * @Description 商品集合
 * @Date 2019/8/9 14:34
 * @Created by wangzx
 */
@Data
public class VsjProductList{

    private List<VsjProductRequest> productList = new ArrayList<>();
}
