package com.vsj.dao;

import com.vsj.mapper.ProductAttrValueMapper;
import com.vsj.model.VsjProductAttrValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname ProductAttrValueDao
 * @Description TODO
 * @Date 2019/7/29 11:01
 * @Created by wangzx
 */
@Component
public class ProductAttrValueDAO {

    @Autowired
    private ProductAttrValueMapper productAttrValueMapper;

    public int insertAttrValue(VsjProductAttrValue v) {
        return productAttrValueMapper.insertAttrValue(v);
    }


}
