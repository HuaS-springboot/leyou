package com.vsj.material.service;


import com.vsj.common.model.BaseResponseParam;

import com.vsj.material.model.request.MaterialCategory;


/**
 * @Classname VsjMaterialCategoryService
 * @Description 分类接口
 * @Date 2019/8/13 16:57
 * @Created by wangzx
 */
public interface VsjMaterialCategoryService {
    Object getMaterialCategory(Integer platformCode);

    Object editMaterialCategroy(MaterialCategory materialCategory,Integer platformCode);

    Object deleteMaterialCategory(MaterialCategory materialCategory,Integer platformCode);

    Object insertMaterialCategory(MaterialCategory materialCategory, Integer platformCode );

    BaseResponseParam findMaterialCategory(Integer platformCode);//MaterialCategory materialCategory,
   /* Object selectMaterialCategory(QueryStat queryStat);*/
}
