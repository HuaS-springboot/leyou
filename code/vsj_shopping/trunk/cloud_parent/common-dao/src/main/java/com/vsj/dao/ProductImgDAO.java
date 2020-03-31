package com.vsj.dao;

import com.vsj.mapper.ProductImgMapper;
import com.vsj.model.VsjProductImg;
import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname ProductImgDao
 * @Description TODO
 * @Date 2019/7/29 11:06
 * @Created by wangzx
 */
@Component
public class ProductImgDAO {

    @Autowired
    private ProductImgMapper productImgMapper;

    public int insertProductImage(VsjProductImg productImg) {
        return productImgMapper.insertProductImage(productImg);
    }

    public List<VsjProductImg> getProductImageList(BaseQueryStat queryStat) {
        return productImgMapper.getProductImageList(queryStat);
    }

    public int updateProductImage(VsjProductImg vsjProductImg) {
        return productImgMapper.updateProductImage(vsjProductImg);
    }

    public int deleteProductImage(BaseQueryStat queryStat) {
        return productImgMapper.deleteProductImage(queryStat);
    }

    public VsjProductImg getProductImageByProductId(Integer productId, Integer platformCode) {
        return productImgMapper.getProductImageByProductId(productId,platformCode);
    }

    public int deleteProductImageByType(Integer type, Integer platformCode) {
        return productImgMapper.deleteProductImageByType(type,platformCode);
    }

    public List<VsjProductImg> getMasteImage(Integer productId, Integer platformCode) {
        return productImgMapper.getMasteImage(productId,platformCode);
    }
}
