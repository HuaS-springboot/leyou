package com.vsj.model.response;

import com.vsj.model.VsjProductAttrValue;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProductAttrResponse
 * @Description TODO
 * @Date 2019/7/31 17:32
 * @Created by wangzx
 */
@Data
public class ProductAttrResponse implements Serializable {
    private static final long serialVersionUID = 6539181364684893999L;

    private Integer id;
    private String attrName;
    private String createTime;
    private Integer platformCode;
    private List<VsjProductAttrValue> attrValueList = new ArrayList<>();
}
