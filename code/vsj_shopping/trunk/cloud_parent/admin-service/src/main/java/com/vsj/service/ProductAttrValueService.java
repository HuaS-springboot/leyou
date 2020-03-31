package com.vsj.service;

import com.vsj.common.utils.StringUtil;
import com.vsj.mapper.ProductAttrKeyMapper;
import com.vsj.model.VsjProductAttrValue;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Classname ProductAttrValue
 * @Description TODO
 * @Date 2019/7/25 17:42
 * @Created by wangzx
 */
public interface ProductAttrValueService {

    int insertAttrValue(VsjProductAttrValue v);
}
