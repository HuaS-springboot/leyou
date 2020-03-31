package com.vsj.material.service;

import com.vsj.material.model.MaterialCategroyGuide;
import com.vsj.material.model.request.MaterialCategroyGuideRequest;
import com.vsj.material.model.request.QueryStat;


/**
 * @Classname MaterialCategroyGuideService
 * @Description 分类导航接口
 * @Date 2019/8/16 18:05
 * @Created by sxm
 */
public interface MaterialCategroyGuideService {


    Object getMaterialCategroyGuide(QueryStat queryStat);

    Object deleteMaterialCategroyGuide(QueryStat queryStat);

    Object updateMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide);

    Object insertMaterialCategroyGuide(MaterialCategroyGuideRequest materialCategroyGuideRequest, Integer platformCode);


}
