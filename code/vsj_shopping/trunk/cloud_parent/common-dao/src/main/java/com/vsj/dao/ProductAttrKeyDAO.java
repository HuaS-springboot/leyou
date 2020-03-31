package com.vsj.dao;

import com.vsj.mapper.ProductAttrKeyMapper;
import com.vsj.model.VsjProductAttrKey;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ProductAttrResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname ProductAttrKeyDao
 * @Description TODO
 * @Date 2019/7/29 10:57
 * @Created by wangzx
 */
@Component
public class ProductAttrKeyDAO {

    @Autowired
    private ProductAttrKeyMapper productAttrKeyMapper;

    public int insertProductAttrKey(VsjProductAttrKey vsjProductAttrKey) {
        return productAttrKeyMapper.insertProductAttrKey(vsjProductAttrKey);
    }


    public List<ProductAttrResponse> getAttrList(BaseQueryStat queryStat) {
        return productAttrKeyMapper.getAttrList(queryStat);
    }
}
