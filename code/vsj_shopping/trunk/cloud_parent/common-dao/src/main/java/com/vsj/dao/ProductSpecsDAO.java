package com.vsj.dao;

import com.vsj.mapper.ProductSpecsMapper;
import com.vsj.model.VsjProductSpecs;

import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname ProductSpecsDao
 * @Description TODO
 * @Date 2019/7/29 11:16
 * @Created by wangzx
 */
@Component
public class ProductSpecsDAO {

    @Autowired
    private ProductSpecsMapper productSpecsMapper;

    public int insertProductSpecs(VsjProductSpecs s) {
        return productSpecsMapper.insertProductSpecs(s);
    }

    public List<VsjProductSpecs> getProductSpecsList(Integer productId) {
        return productSpecsMapper.getProductSpecsList(productId);
    }

    public int updateProductSpecs(VsjProductSpecs vsjProductSpecs) {
        return productSpecsMapper.updateProductSpecs(vsjProductSpecs);
    }

    public int deleteProductSpecs(BaseQueryStat queryStat) {
        return productSpecsMapper.deleteProductSpecs(queryStat);
    }

    public int  deleteProductSpecsByProductId(Integer productId, Integer platformCode) {
        return productSpecsMapper.deleteProductSpecsByProductId(productId,platformCode);

    }
}
