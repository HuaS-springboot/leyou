package com.vsj.material.service.impl;


import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.dao.MaterialCategroyGuideDAO;
import com.vsj.material.model.MaterialCategroyGuide;
import com.vsj.material.model.convert.AbstractObjectConverter;
import com.vsj.material.model.request.MaterialCategroyGuideRequest;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.service.MaterialCategroyGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname MaterialCategroyGuideServiceImpl
 * @Description 分类导航接口实现类
 * @Date 2019/8/16 18:15
 * @Created by sxm
 */
@Service
public class MaterialCategroyGuideServiceImpl implements MaterialCategroyGuideService {

    @Autowired
    private MaterialCategroyGuideDAO materialCategroyGuideDAO;

    @Autowired
    private AbstractObjectConverter<MaterialCategroyGuideRequest,MaterialCategroyGuide> converter;

    @Override
    public Object getMaterialCategroyGuide(QueryStat queryStat) {
        return materialCategroyGuideDAO.getMaterialCategroyGuide(queryStat);
    }

    @Override
    public Object deleteMaterialCategroyGuide(QueryStat queryStat) {
        boolean bool = materialCategroyGuideDAO.deleteMaterialCategroyGuide(queryStat);
        if(bool){
            return BaseResponseParam.success(bool);
        }
        return BaseResponseParam.fail();
    }

    @Override
    public Object updateMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide) {
     boolean bool = materialCategroyGuideDAO.updateMaterialCategroyGuide(materialCategroyGuide);
        if(bool){
            return BaseResponseParam.success(bool);
        }
        return BaseResponseParam.fail();
    }

    @Override
    public Object insertMaterialCategroyGuide(MaterialCategroyGuideRequest materialCategroyGuideRequest, Integer platformCode) {
        MaterialCategroyGuide materialCategroyGuide = converter.convert(materialCategroyGuideRequest,MaterialCategroyGuide.class);
        materialCategroyGuide.setPlatformCode(platformCode);
        boolean bool = materialCategroyGuideDAO.insertMaterialCategroyGuide(materialCategroyGuide);
        if(bool){
            return BaseResponseParam.success(bool);
        }
        return BaseResponseParam.fail();
    }
}
