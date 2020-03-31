package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.request.QueryStat;
import com.vsj.service.ProductCategoryService;
import com.vsj.service.ProductImageService;
import com.vsj.service.ProductService;
import com.vsj.service.VsjActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ProductController
 * @Description 商品相关接口
 * @Date 2019/7/29 13:35
 * @Created by wangzx
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/front/product/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private VsjActivityService vsjActivityService;


    /**
     * @Description 查询首页轮播图/推荐图
     * @Author  wangzx
     * @Date   2019/7/29 13:39
     * @Param  [queryStat]
     * type:位置：0=轮播区域显示图  1=推荐区域显示图
     * status:1 (有效的)
     * @Return java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getProductImageList")
    public Object getProductImageList(QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询首页轮播图/推荐图getProductImageList入参："+queryStat);
        Object obj = productImageService.getProductImageList(queryStat);
        logger.info("查询首页轮播图/推荐图getProductImageList出参："+obj);
        return obj;
    }

    /**
     * @Description 查询商品一二三级分类
     * @Author  wangzx
     * @Date   2019/7/29 14:00
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getProductCategoryList")
    public Object getProductCategoryList(){
        QueryStat queryStat = new QueryStat();
        queryStat.setPlatformCode(getPlatformCode());
        Object obj = productCategoryService.getProductCategoryList(queryStat);
        return obj;
    }


    /**
     * @Description 多条件查询商品列表
     * @Author  wangzx
     * @Date   2019/7/29 14:17
     * @Param  [queryStat]
     * title:商品名称
     * oneCateId：一级分类id
     * twoCateId: 二级分类id
     * status:上下架状态：1=上架
     * pageNum:第几页
     * pageSize:每页几条数据
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getProductList")
    public Object getProductList(QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("小程序首页查询商品列表getProductList入参："+queryStat);
        Object obj = productService.getProductList(queryStat);
        logger.info("小程序首页查询商品列表getProductList出参："+obj);
        return obj;
    }

    /**
     * @Description 小程序首页查询商品详情
     * @Author  wangzx
     * @Date   2019/7/29 14:27
     * @Param  [queryStat]
     * id:商品id
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getProductDetail")
    public Object getProductDetail(QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("小程序首页查询商品详情getProductDetail入参："+queryStat);
        Object obj = productService.getProductDetail(queryStat);
        logger.info("小程序首页查询商品详情getProductDetail出参："+obj);
        return obj;
    }

    /**
     * @Description 小程序首页查询活动列表
     * @Author  wangzx
     * @Date   2019/7/30 10:12
     * pageNum:
     * pageSize:
     * status:1(进行中的)
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getActivityList")
    public Object getActivityList(QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("小程序首页查询活动列表入参：无");
        Object obj = vsjActivityService.getActivityList(queryStat);
        logger.info("小程序首页查询活动列表入参："+obj);
        return obj;
    }

    /**
     * @Description 查询活动详情
     * @Author  wangzx
     * @Date   2019/7/30 10:25
     * @Param  [queryStat]
     * id:活动id
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getActiveDetail")
    public Object getActiveDetail(QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("小程序首页查询活动详情入参："+queryStat);
        Object obj = vsjActivityService.getActiveDetail(queryStat);
        logger.info("小程序首页查询活动详情出参："+obj);
        return obj;
    }
}
