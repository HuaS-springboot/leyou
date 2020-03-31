package com.vsj.service.impl;

import com.vsj.dao.ProductAttrValueDAO;
import com.vsj.model.VsjProductAttrValue;
import com.vsj.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname ProductAttrValueServiceImpl
 * @Description TODO
 * @Date 2019/7/25 17:42
 * @Created by wangzx
 */
@Service
public class ProductAttrValueServiceImpl implements ProductAttrValueService {

    @Autowired
    private ProductAttrValueDAO productAttrValueDao;


    @Override
    public int insertAttrValue(VsjProductAttrValue v) {
        return productAttrValueDao.insertAttrValue(v);
    }
}
