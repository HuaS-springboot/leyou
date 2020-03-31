package com.vsj.service;

import com.vsj.common.model.request.MaterialL;
import com.vsj.common.model.request.QueryStat;
import com.vsj.model.VsjMaterial;
import com.vsj.model.wechat.MaterialList;

import java.util.List;
/**
 * @Author: HuaS
 * @Date :2019/8/8 10:00
 * @Describe:
 */
public interface MaterialService {
    Object getMaterialList(QueryStat queryStat);

    Object insertMaterial(MaterialL materialL);

    Object updateMaterial(MaterialList materialList,Integer platformCode);

    Object deleteMaterial(VsjMaterial vsjMaterial);
}
