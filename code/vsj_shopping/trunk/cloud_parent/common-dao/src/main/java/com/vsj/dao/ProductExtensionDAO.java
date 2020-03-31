package com.vsj.dao;

import com.vsj.mapper.ProductExtensionMapper;
import com.vsj.model.VsjProductExtension;

import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname ProductExtensionDao
 * @Description TODO
 * @Date 2019/7/29 11:20
 * @Created by wangzx
 */
@Component
public class ProductExtensionDAO {

    @Autowired
    private ProductExtensionMapper productExtensionMapper;

    public int insertProductExtension(VsjProductExtension vsjProductExtension) {
        return productExtensionMapper.insertProductExtension(vsjProductExtension);
    }

    public List<VsjProductExtension> getExtensionList(BaseQueryStat queryStat) {
        return productExtensionMapper.getExtensionList(queryStat);
    }

    public int updateExtension(VsjProductExtension vsjProductExtension) {
        return productExtensionMapper.updateExtension(vsjProductExtension);
    }

    public int deleteExtension(BaseQueryStat queryStat) {
        return productExtensionMapper.deleteExtension(queryStat);
    }
}
