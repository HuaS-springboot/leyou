package com.vsj.material.service.impl;

import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.dao.VsjMaterialCategoryDAO;
import com.vsj.material.model.VsjMaterialCategory;
import com.vsj.material.model.convert.AbstractObjectConverter;
import com.vsj.material.model.request.MaterialCategory;
import com.vsj.material.service.VsjMaterialCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname VsjMaterialCategoryServiceImpl
 * @Description 分类实现类
 * @Date 2019/8/13 16:57
 * @Created by wangzx
 */
@Service
public class VsjMaterialCategoryServiceImpl implements VsjMaterialCategoryService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjMaterialCategoryDAO vsjMaterialCategoryDAO;
    @Autowired
    private AbstractObjectConverter<MaterialCategory,VsjMaterialCategory> convert;

    @Override
    public Object getMaterialCategory(Integer platformCode) {
        Object obj = vsjMaterialCategoryDAO.getMaterialCategory(platformCode);
        return BaseResponseParam.success(obj);
    }

    @Override
    public Object editMaterialCategroy(MaterialCategory materialCategory,Integer platformCode) {
        VsjMaterialCategory vsjMaterialCategory = convert.convert(materialCategory, VsjMaterialCategory.class);
        if (null != vsjMaterialCategory) {
            vsjMaterialCategory.setPlatformCode(platformCode);
        }
        if (null == vsjMaterialCategory) {
            logger.debug("产品分类转换失败insertProductCategory...");
            return BaseResponseParam.fail();
        }

        if(null == vsjMaterialCategory.getId()){
            return BaseResponseParam.fail("参数不能为空");
        }
        int count = vsjMaterialCategoryDAO.editMaterialCategroy(vsjMaterialCategory);
        if(count>0){
            List<VsjMaterialCategory> vsjMaterialCategoryList = vsjMaterialCategory.getChildren();
            if(vsjMaterialCategoryList != null){
                for(VsjMaterialCategory vsjMaterialCategory1 : vsjMaterialCategoryList){
                    if(vsjMaterialCategory1.getId()!=null){
                        vsjMaterialCategoryDAO.editMaterialCategroy(vsjMaterialCategory1);
                    }else{
                        vsjMaterialCategory1.setPlatformCode(platformCode);
                        vsjMaterialCategory1.setParentId(materialCategory.getId());
                        vsjMaterialCategoryDAO.insertMaterialCategory(vsjMaterialCategory1);
                    }
                    List<VsjMaterialCategory> vsjMaterialCategoryList2 = vsjMaterialCategory1.getChildren();
                    for(VsjMaterialCategory vsjMaterialCategory2 : vsjMaterialCategoryList2){
                        if(vsjMaterialCategory2.getId()!= null){
                            vsjMaterialCategoryDAO.editMaterialCategroy(vsjMaterialCategory2);
                        }else{
                            vsjMaterialCategory2.setParentId(vsjMaterialCategory1.getId());
                            vsjMaterialCategory2.setPlatformCode(platformCode);
                            vsjMaterialCategoryDAO.insertMaterialCategory(vsjMaterialCategory2);
                        }
                    }

                }
            }
        }
        return BaseResponse.success();
    }

    @Override
    public Object deleteMaterialCategory(MaterialCategory materialCategory, Integer platformCode) {
        VsjMaterialCategory vsjMaterialCategory = convert.convert(materialCategory, VsjMaterialCategory.class);
        if (null != vsjMaterialCategory) {
            vsjMaterialCategory.setPlatformCode(platformCode);
        }
        if (null == vsjMaterialCategory) {
            logger.debug("产品分类转换失败insertProductCategory...");
            return BaseResponseParam.fail();
        }

        if (vsjMaterialCategory.getId() != null) {
            int count = vsjMaterialCategoryDAO.deleteMaterialCategory(vsjMaterialCategory);
            if (count > 0) {
                Integer id = vsjMaterialCategory .getId();
                VsjMaterialCategory vsjMaterialCategory1 = new VsjMaterialCategory();
                vsjMaterialCategory1.setParentId(id);
                vsjMaterialCategory1.setPlatformCode(platformCode);
                Integer id2 = null;
                if (vsjMaterialCategory1.getParentId() != null) {
                    List<VsjMaterialCategory> vsjMaterialCategoryList1 = vsjMaterialCategoryDAO.findMaterialCategoryOne(vsjMaterialCategory1);
                    for (VsjMaterialCategory vsjMaterialCategoryA : vsjMaterialCategoryList1) {
                        vsjMaterialCategoryDAO.deleteMaterialCategory(vsjMaterialCategoryA);
                        id2 = vsjMaterialCategoryA.getId();
                        VsjMaterialCategory vsjMaterialCategory2 = new VsjMaterialCategory();
                        vsjMaterialCategory2.setParentId(id2);
                        vsjMaterialCategory2.setPlatformCode(platformCode);
                        if (vsjMaterialCategory2.getParentId() != null) {
                            List<VsjMaterialCategory> vsjMaterialCategoryList2 = vsjMaterialCategoryDAO.findMaterialCategoryOne(vsjMaterialCategory2);
                            for (VsjMaterialCategory vsjMaterialCategoryB : vsjMaterialCategoryList2) {
                                vsjMaterialCategoryDAO.deleteMaterialCategory(vsjMaterialCategoryB);
                                return BaseResponse.success();
                            }
                        }

                    }
                }
            }

        }
        return BaseResponse.success();
    }

    @Override
    public Object insertMaterialCategory(MaterialCategory materialCategory,Integer platformCode ) {
        VsjMaterialCategory vsjMaterialCategory = convert.convert(materialCategory, VsjMaterialCategory.class);
        if (null != vsjMaterialCategory) {
            vsjMaterialCategory.setPlatformCode(platformCode);
        }
        if (null == vsjMaterialCategory) {
            logger.debug("产品分类转换失败insertProductCategory...");
            return BaseResponseParam.fail();
        }
        int count = vsjMaterialCategoryDAO.insertMaterialCategory(vsjMaterialCategory);
        if (count > 0) {
            List<VsjMaterialCategory> vsjMaterialCategoryList = vsjMaterialCategory.getChildren();

            if (vsjMaterialCategoryList != null) {
                for (VsjMaterialCategory vsjMaterialCategoryChildren : vsjMaterialCategoryList) {
                    vsjMaterialCategoryChildren.setParentId(vsjMaterialCategory.getId());
                    vsjMaterialCategoryChildren.setPlatformCode(platformCode);
                    int count1 = vsjMaterialCategoryDAO.insertMaterialCategory(vsjMaterialCategoryChildren);

                    List<VsjMaterialCategory> vsjMaterialCategoryChildren2 = vsjMaterialCategoryChildren .getChildren();

                    if (vsjMaterialCategoryChildren2 != null) {
                        for (VsjMaterialCategory materialCategoryChildren2 : vsjMaterialCategoryChildren2) {
                            materialCategoryChildren2.setParentId(vsjMaterialCategoryChildren.getId());
                            materialCategoryChildren2.setPlatformCode(platformCode);
                            int count2 = vsjMaterialCategoryDAO.insertMaterialCategory(materialCategoryChildren2);
                        }
                    }

                }
            }
            return BaseResponseParam.success();
        }
        return BaseResponse.fail();
    }

    @Override
    public BaseResponseParam findMaterialCategory(Integer platformCode) {
//        VsjMaterialCategory vsjMaterialCategory = convert.convert(materialCategory, VsjMaterialCategory.class);
//        if (null != vsjMaterialCategory) {
//            vsjMaterialCategory.setPlatformCode(platformCode);
//        }
//        if (null == vsjMaterialCategory) {
//            logger.debug("产品分类转换失败insertProductCategory...");
//            return BaseResponseParam.fail();
//        }

        if(platformCode == null){
            return BaseResponseParam.fail("平台码不能为空");
        }
        List<VsjMaterialCategory> vsjMaterialCategoryList = vsjMaterialCategoryDAO.findMaterialCategory(platformCode);//vsjMaterialCategory
        return BaseResponseParam.success(vsjMaterialCategoryList);
    }


}
