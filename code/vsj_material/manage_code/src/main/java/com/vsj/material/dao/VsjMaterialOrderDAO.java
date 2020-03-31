package com.vsj.material.dao;

import com.vsj.material.mapper.VsjMaterialOrderMapper;
import com.vsj.material.model.MaterialOrder;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.VsjMaterialOrderResponse;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname VsjMaterialOrderDAO
 * @Description 订单DAO
 * @Date 2019/8/15 9:15
 * @Created by wangzx
 */
@Component
public class VsjMaterialOrderDAO {

    @Autowired
    private VsjMaterialOrderMapper vsjMaterialOrderMapper;


    public List<VsjMaterialOrderResponse> getMemberOrderList(QueryStat queryStat) {
        return vsjMaterialOrderMapper.getMemberOrderList(queryStat);
    }

    public int insertMaterialOrder(MaterialOrder materialOrder) {
        return vsjMaterialOrderMapper.insertMaterialOrder(materialOrder);
    }

    public MaterialOrder getMaterialOrderByOrderNo(String orderNo, Integer platformCode) {
        return vsjMaterialOrderMapper.getMaterialOrderByOrderNo(orderNo,platformCode);
    }

    public int updateMaterial(MaterialOrder materialOrder) {
        return vsjMaterialOrderMapper.updateMaterial(materialOrder);
    }
}
