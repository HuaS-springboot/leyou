package com.vsj.dao;

import com.alibaba.fastjson.JSON;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.StringUtil;
import com.vsj.mapper.ProductCategoryMapper;
import com.vsj.model.VsjProductCategory;
import com.vsj.model.request.BaseQueryStat;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductCategoryDAO {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private RedisClient redisClient;

    public int insertProductCategory(VsjProductCategory vsjProductCategory) {
        return productCategoryMapper.insertProductCategory(vsjProductCategory);
    }

    public void deleteProductCateKey() {
        redisClient.remove(RedisKeyConstant.PRODUCT_CATEGORY_KEY);
    }

    public int updateProductCategory(VsjProductCategory vsjProductCategory){
        return productCategoryMapper.updateProductCategory(vsjProductCategory);
    }


    public int deleteProductCategory(VsjProductCategory vsjProductCategory){
        return productCategoryMapper.deleteProductCategory(vsjProductCategory);
    }

    public List<VsjProductCategory> selectProductCategoryDetails(VsjProductCategory vsjProductCategory){
        return productCategoryMapper.selectProductCategoryDetails(vsjProductCategory);
    }

    public List<VsjProductCategory> getProductCategoryList(BaseQueryStat queryStat) {
        // 获取redis中的数据
        String redisStr = (String) redisClient.get(RedisKeyConstant.PRODUCT_CATEGORY_KEY);
        if(!StringUtil.isEmptyStr(redisStr)){
            List<VsjProductCategory> rootList = JSON.parseArray(redisStr, VsjProductCategory.class);
            return rootList;
        }
        // 数据库的数据
        List<VsjProductCategory> rootList = getDbCategoryList(queryStat);
        // 数据放到redis中
        redisClient.set(RedisKeyConstant.PRODUCT_CATEGORY_KEY, JSON.toJSONString(rootList));
        return rootList;
    }

    private List<VsjProductCategory> getDbCategoryList(BaseQueryStat queryStat) {
        // 保存结构
        List<VsjProductCategory> rootList = new ArrayList<>();
        // 数据库数据
        List<VsjProductCategory> dbCateList = productCategoryMapper.findProductCategory(queryStat);
        // 保存到map中
        Map<Integer, VsjProductCategory> permissionMap = new HashMap<>();
        for (VsjProductCategory category : dbCateList) {
            VsjProductCategory child = category;
            permissionMap.put(category.getId(), category);
            if(0 == category.getParentId()){
                // 获取root
                rootList.add(category);
            }else {
                // 根据child的parentCode当做key获取父级
                VsjProductCategory parent = permissionMap.get(child.getParentId());
                if(parent != null){
                    parent.getChildren().add(child);
                }
            }
        }
        return rootList;
    }
}
