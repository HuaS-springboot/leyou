package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MaterialL;
import com.vsj.common.model.request.QueryStat;
import com.vsj.dao.MaterialDao;
import com.vsj.model.VsjMaterial;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.wechat.MaterialList;
import com.vsj.service.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/8/8 10:04
 * @Describe:
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialDao materialDao;

    @Autowired
    private AbstractObjectConverter<MaterialL, VsjMaterial> convert;

    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;

    @Override
    public Object getMaterialList(QueryStat queryStat) {
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<VsjMaterial> vsjMaterials = materialDao.getMaterialList();
        PageInfo<VsjMaterial> info = new PageInfo<>(vsjMaterials);
        return BaseResponseParam.success(info);
    }

    @Override
    public Object insertMaterial(MaterialL materialL) {
        VsjMaterial vsjMaterial = null;
        vsjMaterial = convert.convert(materialL, VsjMaterial.class);
        if (null == vsjMaterial) {
            logger.debug("素材管理转换失败insertMaterial...");
            return BaseResponseParam.fail();
        }
        int i = materialDao.insertMaterial(vsjMaterial);
        if (i > 0) {
            return BaseResponse.success();
        }
        return BaseResponse.fail();
    }


    @Override
    public Object updateMaterial(MaterialList materialList,Integer platformCode) {
        List<VsjMaterial> list = materialList.getMaterialList();
        if (list.isEmpty()) {
            return BaseResponse.fail("参数错误");
        }
        int i=0;
        for (VsjMaterial vsjMaterial : list) {
            vsjMaterial.setPlatformCode(platformCode);
            i = materialDao.updateMaterial(vsjMaterial);
        }
        if (i>0){
            return BaseResponseParam.success();
        }

        return BaseResponseParam.fail();
    }

    @Override
    public Object deleteMaterial(VsjMaterial vsjMaterial) {
        if (vsjMaterial.getId() != null) {
            int i = materialDao.deleteMaterial(vsjMaterial);
            if (i > 0) {
                return BaseResponseParam.success();
            }
            return BaseResponseParam.fail();
        }
        return BaseResponseParam.fail("参数错误");
    }
}
