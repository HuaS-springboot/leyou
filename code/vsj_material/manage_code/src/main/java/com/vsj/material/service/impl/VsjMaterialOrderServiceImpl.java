package com.vsj.material.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.dao.VsjMaterialOrderDAO;
import com.vsj.material.model.MaterialOrder;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.VsjMaterialOrderResponse;
import com.vsj.material.service.VsjMaterialOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname VsjMaterialOrderServiceImpl
 * @Description 订单相关实现类
 * @Date 2019/8/15 9:15
 * @Created by wangzx
 */
@Service
public class VsjMaterialOrderServiceImpl implements VsjMaterialOrderService {

    @Autowired
    private VsjMaterialOrderDAO vsjMaterialOrderDAO;


    @Override
    public Object getMemberOrderList(QueryStat queryStat) {
        if(queryStat.getPlatformCode() == null){
            return BaseResponse.fail("参数错误");
        }
        PageHelper.startPage(queryStat.getPageNum(),queryStat.getPageSize());
        List<VsjMaterialOrderResponse> list = vsjMaterialOrderDAO.getMemberOrderList(queryStat);
        PageInfo<VsjMaterialOrderResponse> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public int insertMaterialOrder(MaterialOrder materialOrder) {
        return vsjMaterialOrderDAO.insertMaterialOrder(materialOrder);
    }

    @Override
    public MaterialOrder getMaterialOrderByOrderNo(String orderNo, Integer platformCode) {
        return vsjMaterialOrderDAO.getMaterialOrderByOrderNo(orderNo,platformCode);
    }

    @Override
    public int updateMaterial(MaterialOrder materialOrder) {
        return vsjMaterialOrderDAO.updateMaterial(materialOrder);
    }
}
