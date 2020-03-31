package com.vsj.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.ProductCategory;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.model.request.VsjProductCategoryList;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.dao.ProductCategoryDAO;
import com.vsj.model.VsjProductCategory;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.ProductCatetoryService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCatetoryService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductCategoryDAO productCategoryDAO;
    @Autowired
    private AbstractObjectConverter<ProductCategory, VsjProductCategory> convert;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;

    @Override
    public Object insertProductCategory(ProductCategory productCategory, Integer platformCode) {
        VsjProductCategory vsjProductCategory = null;
        vsjProductCategory = convert.convert(productCategory, VsjProductCategory.class);
        if (null != vsjProductCategory) {
            vsjProductCategory.setPlatformCode(platformCode);
        }
        if (null == vsjProductCategory) {
            logger.debug("产品分类转换失败insertProductCategory...");
            return BaseResponseParam.fail();
        }

        int count = productCategoryDAO.insertProductCategory(vsjProductCategory);
        if (count > 0) {

            List<VsjProductCategory> productCategoryChildrenList = vsjProductCategory.getChildren();


            if (productCategoryChildrenList != null) {
                for (VsjProductCategory productCategoryChildren : productCategoryChildrenList) {
                    productCategoryChildren.setParentId(vsjProductCategory.getId());
                    productCategoryChildren.setPlatformCode(platformCode);
                    int count1 = productCategoryDAO.insertProductCategory(productCategoryChildren);

                    List<VsjProductCategory> productCategoryChildrenList2 = productCategoryChildren.getChildren();


                    if (productCategoryChildrenList2 != null) {
                        for (VsjProductCategory productCategoryChildren2 : productCategoryChildrenList2) {
                            productCategoryChildren2.setParentId(productCategoryChildren.getId());
                            productCategoryChildren2.setPlatformCode(platformCode);
                            int count2 = productCategoryDAO.insertProductCategory(productCategoryChildren2);
                        }
                    }

                }
            }

            redisClient.remove(RedisKeyConstant.PRODUCT_CATEGORY_KEY);
            return BaseResponseParam.success();
        }
        return BaseResponse.fail();
    }

    @Override
    public Object updateProductCategory(ProductCategory productCategory, Integer platformCode) {
//        logger.debug("productCategory = {}", JSON.toJSONString(productCategory));
        VsjProductCategory vsjProductCategory =  convert.convert(productCategory, VsjProductCategory.class);

        if (null != vsjProductCategory) {
            vsjProductCategory.setPlatformCode(platformCode);
        }
        if (null == vsjProductCategory) {
            logger.debug("产品分类转换失败insertProductCategory...");
            return BaseResponseParam.fail();
        }
        //处理一级分类信息
        Integer parentId = editProductCategory(vsjProductCategory, null, platformCode);
        if (parentId < 0) {
            return BaseResponseParam.fail();
        }
        //处理二层分类信息                      productCategory
        for (VsjProductCategory secondCategory : productCategory.getChildren()) {
            Integer secondParentId = secondCategory.getId();
//            VsjProductCategory secondProductCategory =  convert.convert(secondCategory, VsjProductCategory.class);
            if (secondCategory != null) {
                secondParentId = editProductCategory(secondCategory, parentId, platformCode);
                if (secondParentId < 0) {
                    return BaseResponseParam.fail();
                }

            }
            //处理三级分类
            for (VsjProductCategory thirdCategory : secondCategory.getChildren()) {
                if (thirdCategory != null) {
//                    VsjProductCategory thirdProductCategory =  convert.convert(thirdCategory, VsjProductCategory.class);
                    int thridParentId = editProductCategory(thirdCategory, secondParentId, platformCode);
                    if (thridParentId < 0) {
                        return BaseResponseParam.fail();
                    }
                }

            }
        }

        return BaseResponse.success();
    }

    private Integer editProductCategory(VsjProductCategory vsjProductCategory, Integer parentId, Integer platformCode) {
        vsjProductCategory.setParentId(parentId);
        vsjProductCategory.setPlatformCode(platformCode);

        if (vsjProductCategory.getTypeNum() == null) {
            return vsjProductCategory.getId();
        }
        //对typeNum = 0，1，2，3的判断
        Integer typeNum = vsjProductCategory.getTypeNum();
        if (typeNum == 0 ) {
            return vsjProductCategory.getId();
        }

        typeNum = typeNum.intValue();

        if (typeNum == 1 ) {//修改
            int count = productCategoryDAO.updateProductCategory(vsjProductCategory);
            if (count > 0) {
                return vsjProductCategory.getId();
            }
            return -2;
        }
        if (typeNum == 2) {//添加
            int count = productCategoryDAO.insertProductCategory(vsjProductCategory);
            if (count > 0) {
                return vsjProductCategory.getId();
            }
            return -2;
        }
        if (typeNum == -1) {//删除
            int count = productCategoryDAO.deleteProductCategory(vsjProductCategory);
            if (count > 0) {
                return vsjProductCategory.getId();
            }
            return -2;
        }
        return -2;
    }


    @Override
    public Object deleteProductCategory(ProductCategory productCategory, Integer platformCode) {
        VsjProductCategory vsjProductCategory = null;
        vsjProductCategory = convert.convert(productCategory, VsjProductCategory.class);
        vsjProductCategory.setPlatformCode(platformCode);
        if (null == vsjProductCategory) {
            logger.debug("产品分类转换失败deleteProductCategory...");
            return BaseResponseParam.fail();
        }

        if (vsjProductCategory.getId() != null) {
            int count = productCategoryDAO.deleteProductCategory(vsjProductCategory);
            if (count > 0) {
                Integer id = vsjProductCategory.getId();
                VsjProductCategory vsjProductCategory1 = new VsjProductCategory();
                vsjProductCategory1.setParentId(id);
                vsjProductCategory1.setPlatformCode(platformCode);
                Integer id2 = 0;
                if (vsjProductCategory1.getParentId() != null) {
                    List<VsjProductCategory> vsjProductCategoryList = productCategoryDAO.selectProductCategoryDetails(vsjProductCategory1);
                    for (VsjProductCategory vsjProductCategoryA : vsjProductCategoryList) {
                        productCategoryDAO.deleteProductCategory(vsjProductCategoryA);
                        id2 = vsjProductCategoryA.getId();
                    }
                }

                VsjProductCategory vsjProductCategory2 = new VsjProductCategory();
                vsjProductCategory2.setParentId(id2);
                vsjProductCategory2.setPlatformCode(platformCode);
                if (vsjProductCategory2.getParentId() != null) {
                    List<VsjProductCategory> vsjProductCategoryList = productCategoryDAO.selectProductCategoryDetails(vsjProductCategory2);
                    for (VsjProductCategory vsjProductCategoryB : vsjProductCategoryList) {
                        productCategoryDAO.deleteProductCategory(vsjProductCategoryB);
                    }
                }

                return BaseResponse.success();
            }
            return BaseResponse.fail();
        }

        return BaseResponse.fail("id或code码不能为空");
    }


    @Override
    public Object selectProductCategoryDetails(ProductCategory productCategory) {
        VsjProductCategory vsjProductCategory = null;
        vsjProductCategory = convert.convert(productCategory, VsjProductCategory.class);
        if (null == vsjProductCategory) {
            logger.debug("产品分类转换失败selectProductCategoryDetails...");
            return BaseResponseParam.fail();
        }
        //===============================================================================
        List<VsjProductCategory> vsjProductCategoryList = productCategoryDAO.selectProductCategoryDetails(vsjProductCategory);
        return BaseResponseParam.success(vsjProductCategoryList);
    }

    @Override
    public BaseResponseParam getProductCategoryList(QueryStat queryStat) {
        if (queryStat.getPlatformCode() == null) {
            return BaseResponseParam.fail("平台码不能为空");
        }
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat,BaseQueryStat.class);
        List<VsjProductCategory> productCategoryList = productCategoryDAO.getProductCategoryList(baseQueryStat);
        return BaseResponseParam.success(productCategoryList);
    }
}
