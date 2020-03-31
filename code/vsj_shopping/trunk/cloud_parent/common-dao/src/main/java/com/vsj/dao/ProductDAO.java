package com.vsj.dao;

import com.vsj.mapper.ProductMapper;
import com.vsj.model.VsjProduct;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname ProductDao
 * @Description TODO
 * @Date 2019/7/29 11:11
 * @Created by wangzx
 */
@Component
public class ProductDAO {

    @Autowired
    private ProductMapper productMapper;

    public int insertProduct(VsjProduct vsjProduct) {
        return productMapper.insertProduct(vsjProduct);
    }

    public List<VsjProduct> getProductList(BaseQueryStat querySet) {
        return productMapper.getProductList(querySet);
    }

    public int updateProStatus(BaseQueryStat queryStat) {
        return productMapper.updateProStatus(queryStat);
    }

    public ProductResponse getProductDetail(BaseQueryStat queryStat) {
        return productMapper.getProductDetail(queryStat);
    }

    public int updateProduct(VsjProduct vsjProduct) {
        return productMapper.updateProduct(vsjProduct);
    }
}
