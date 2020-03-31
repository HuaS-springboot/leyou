package com.vsj.controller;

import com.alibaba.fastjson.JSON;
import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.*;
import com.vsj.model.response.ParentDist;
import com.vsj.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname ProductAdminController
 * @Description 商品管理相关接口
 * @Date 2019/7/23 16:14
 * @Created by wangzx
 */
@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/product/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductAdminController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductExtensionService productExtensionService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private ProductAttrKeyService productAttrKeyService;
    @Autowired
    private DistTemplateService distTemplateService;
    @Autowired
    private ProductSpecsService productSpecsService;
    @Autowired
    private StageSchemaService stageSchemaService;
    @Autowired
    private ProductImgService productImgService;
    @Autowired
    private ProductCatetoryService productCatetoryService;
    @Autowired
    private VsjStageDistrService vsjStageDistrService;


    /**
     * @Description 获取商品一二三级分类
     * @Author  wangzx
     * @Date   2019/7/31 15:43
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询商品一二三级分类", notes = "查询商品一二三级分类", httpMethod = "POST")
    @PostMapping("getProductCategoryList")
    public BaseResponseParam getProductCategoryList(){
        QueryStat queryStat = new QueryStat();
        queryStat.setPlatformCode(getPlatformCode());
        BaseResponseParam response = productCatetoryService.getProductCategoryList(queryStat);
        logger.info("查询商品一二三级分类出参："+response);
        return response;
    }


    /**
     * @Description 添加商品信息
     * @Author  wangzx
     * @Date   2019/7/24 15:51
     * @Param  [vsjProduct]
     * productName：商品名称
     * productImage：商品图片
     * oneCategoryId：一级分类id
     * twoCategoryId: 二级分类id
     * saleTime: 销售时间
     * isExchange：是否支持退/换货 0=不支持  1=支持
     * weight：重量
     * productSort：权重
     * publishStatus:上下架状态：0=下架;1=上架
     * shareImage:分享图片地址
     * shareTitle:分享标题
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "添加商品信息", notes = "添加商品信息", httpMethod = "POST")
    @PostMapping("insertProduct")
    public BaseResponseParam insertProduct(@RequestBody VsjProductRequest vsjProductRequest){
        logger.info("添加商品信息insertProduct入参={}",vsjProductRequest);
        Integer platformCode = getPlatformCode();
        BaseResponseParam response = productService.insertProduct(vsjProductRequest,platformCode);
        logger.info("添加商品信息insertProduct出参={}",response);
        return response;
    }

    /**
     * @Description 查询商品列表
     * @Author  wangzx
     * @Date   2019/7/23 17:12
     * @Param  [querySet]
     * title:商品名称
     * oneCateId：一级分类id
     * twoCateId: 二级分类id
     * threeCateId: 三级商品id
     * status:上下架状态：0=下架;1=上架
     * priceMax:价格上限
     * priceMin:价格下限
     * pageNum:第几页
     * pageSize:每页几条数据
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询商品列表", notes = "查询商品列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "商品名称"),
            @ApiImplicitParam(name = "oneCateId",value = "一级分类id"),
            @ApiImplicitParam(name = "twoCateId",value = "二级分类id"),
            @ApiImplicitParam(name = "status",value = "上下架状态：0=下架;1=上架"),
            @ApiImplicitParam(name = "priceMax",value = "价格上限"),
            @ApiImplicitParam(name = "priceMin",value = "价格下限"),
            @ApiImplicitParam(name = "pageNum",value = "第几页"),
            @ApiImplicitParam(name = "pageSize",value = "每页几条数据")
    })
    @PostMapping("getProductList")
    public BaseResponseParam getProductList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询商品列表getProductList入参={}",queryStat);
        BaseResponseParam response = productService.getProductList(queryStat);
        logger.info("查询商品列表getProductList出参={}",response);
        return response;
    }

    /**
     * @Description 查询商品详情
     * @Author  wangzx
     * @Date   2019/7/25 13:42
     * @Param  [queryStat]
     * id:商品id
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品id")
    })
    @PostMapping("getProductDetail")
    public BaseResponseParam getProductDetail(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询商品详情getProductDetail入参={}",queryStat);
        BaseResponseParam response = productService.getProductDetail(queryStat);
        logger.info("查询商品详情getProductDetail出参={}",response);
        return response;
    }


    /**
     * @Description 修改商品信息
     * @Author  wangzx
     * @Date   2019/7/25 14:16
     * @Param  [vsjProduct]
     * productiId:商品id
     * productName：商品名称
     * productImage：商品图片
     * oneCategoryId：一级分类id
     * twoCategoryId: 二级分类id
     * threeCateGoryId:三级分类id
     * saleTime: 销售时间
     * isExchange：是否支持退/换货 0=不支持  1=支持
     * weight：重量
     * productSort：权重
     * publishStatus:上下架状态：0=下架;1=上架
     * describe：商品描述
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "修改商品详情", notes = "修改商品详情", httpMethod = "POST")
    @PostMapping("updateProduct")
    public BaseResponseParam updateProduct(@RequestBody VsjProductRequest vsjProductRequest){
        logger.info("修改商品详情updateProduct入参={}",vsjProductRequest);
        BaseResponseParam response = productService.updateProduct(vsjProductRequest, getPlatformCode());
        logger.info("修改商品详情updateProduct出参={}",response);
        return response;
    }


    /**
     * @Description 添加商品扩展属性
     * @Author  wangzx
     * @Date   2019/7/24 18:25
     * @Param  [vsjProductExtension]
     * productId: 商品id
     * type: 类型 0=商品属性
     * extensionKey：扩展名称
     * extensionValue：扩展值
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "添加商品扩展属性",notes = "添加商品扩展属性",httpMethod = "POST")
    @PostMapping("insertProductExtension")
    public BaseResponseParam insertProductExtension(@RequestBody VsjProductExtensionRequestList vsjProductExtensionRequestList){
        logger.info("添加商品属性insertProductExtension入参={}",vsjProductExtensionRequestList);
        BaseResponseParam response = productExtensionService.insertProductExtension(vsjProductExtensionRequestList, getPlatformCode());
        logger.info("添加商品属性insertProductExtension出参={}",response);
        return response;
    }

    /**
     * @Description 查询商品扩展属性列表
     * @Author  wangzx
     * @Date   2019/7/25 9:53
     * @Param  [queryStat]
     * id:商品id
     * pageNum:第几页
     * pageSize:每页几条数据
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getExtensionList")
    @ApiOperation(value = "查询商品扩展属性列表", notes = "查询商品扩展属性列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品id"),
            @ApiImplicitParam(name = "pageNum",value = "第几页"),
            @ApiImplicitParam(name = "pageSize",value = "每页几条数据"),
    })
    public BaseResponseParam getExtensionList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询商品扩展属性getExtensionList入参={}",queryStat);
        BaseResponseParam response = productExtensionService.getExtensionList(queryStat);
        logger.info("查询商品扩展属性getExtensionList出参={}",response);
        return response;
    }


    /**
     * @Description 修改商品扩展属性
     * @Author  wangzx
     * @Date   2019/7/25 10:29
     * @Param  [vsjProductExtension]
     * id:扩展属性id
     * extensionKey:属性名
     * extensionValue:属性值
     * type:类型
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("updateExtension")
    @ApiOperation(value = "修改商品扩展属性", notes = "修改商品扩展属性", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "扩展属性id",required = true),
            @ApiImplicitParam(name = "extensionKey",value = "属性名"),
            @ApiImplicitParam(name = "extensionValue",value = "属性值"),
            @ApiImplicitParam(name = "type",value = "类型"),
    })
    public BaseResponseParam updateExtension(@RequestBody VsjProductExtensionRequestList vsjProductExtensionRequestList){
        logger.info("修改商品扩展属性updateExtension入参={}",vsjProductExtensionRequestList);
        BaseResponseParam response = productExtensionService.updateExtension(vsjProductExtensionRequestList, getPlatformCode());
        logger.info("修改商品扩展属性updateExtension出参={},response");
        return response;
    }

    /**
     * @Description 删除商品扩展属性
     * @Author  wangzx
     * @Date   2019/7/25 10:38
     * @Param  [queryStat]
     * id:扩展属性id
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("deleteExtension")
    @ApiOperation(value = "删除商品扩展属性", notes = "删除商品扩展属性", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "扩展属性id",required = true)
    })
    public BaseResponseParam deleteExtension(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("删除商品扩展属性deleteExtension入参={}",queryStat);
        BaseResponseParam response = productExtensionService.deleteExtension(queryStat);
        logger.info("删除商品扩展属性deleteExtension出参={}",response);
        return response;
    }

    /**
     * @Description 批量更改商品上/下架/删除
     * @Author  wangzx
     * @Date   2019/7/25 11:34
     * @Param  [queryStat]
     * ids：商品id 如：1,5,6
     * status：状态 -1=删除;0=下架/1=上架
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @PostMapping("updateProStatus")
    @ApiOperation(value = "批量更改商品上/下架/删除", notes = "批量更改商品上/下架/删除", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "商品id 如：1,5,6",required = true),
            @ApiImplicitParam(name = "status",value = "状态 0=下架/1=上架;-1=删除",required = true)
    })
    public BaseResponseParam updateProStatus(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("批量更改商品上/下架/删除updateProStatus入参={}",queryStat);
        BaseResponseParam response = productService.updateProStatus(queryStat);
        logger.info("批量更改商品上/下架/删除updateProStatus出参={}",response);
        return response;
    }

    /**
     * @Description 批量修改商品信息(权重,图片销量等)
     * @Author  wangzx
     * @Date   2019/8/9 14:38
     * @Param  [vsjProductList]
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "批量修改商品信息(权重,图片销量等)", notes = "批量修改商品信息(权重,图片销量等)", httpMethod = "POST")
    @PostMapping("updateProductBatch")
    public BaseResponseParam updateSortBatch(@RequestBody VsjProductList vsjProductList){
        logger.info("批量修改商品信息入参={}",vsjProductList);
        BaseResponseParam response = productService.updateProductBatch(vsjProductList,getPlatformCode());
        logger.info("批量修改商品信息出参={}",response);
        return response;
    }

    /**
     * @Description 获取系统省市区
     * @Author  wangzx
     * @Date   2019/7/25 15:17
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getSysAreas")
    @ApiOperation(value = "获取系统省市区", notes = "获取系统省市区", httpMethod = "POST")
    public BaseResponseParam getSysAreas(){
        QueryStat queryStat = new QueryStat();
        queryStat.setPlatformCode(getPlatformCode());
        BaseResponseParam response = sysAreaService.getSysAreas(queryStat);
        return response;
    }

    /**
     * @Description 添加规格到规格列表
     * @Author  wangzx
     * @Date   2019/7/25 18:09
     * @Param  [proAttrList]
     * attrName: 规格名称
     * nameSort：规格名称排序
     * attrValueList：对应规格名称的值
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("insertAttr")
    @ApiOperation(value = "添加规格到规格列表", notes = "添加规格到规格列表", httpMethod = "POST")
    public BaseResponseParam insertAttr(@RequestBody ProAttrList proAttrList){
        proAttrList.setPlatformCode(getPlatformCode());
        logger.info("添加商品规格列表insertAttr入参={}",proAttrList);
        BaseResponseParam response = productAttrKeyService.insertProductAttrKey(proAttrList);
        logger.info("添加商品规格列表insertAttr出参={}",response);
        return response;
    }

    /**
     * @Description 获取规格列表
     * @Author  wangzx
     * @Date   2019/7/31 17:28
     * @Param  [queryStat]
     * pageNum:
     * pageSize:
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "获取规格列表", notes = "获取规格列表", httpMethod = "POST")
    @PostMapping("getAttrList")
    public BaseResponseParam getAttrList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("获取规格列表入参={}",queryStat);
        BaseResponseParam response = productAttrKeyService.getAttrList(queryStat);
        logger.info("获取规格列表出参={}",response);
        return response;
    }


    /**
     * @Description 自定义全国/定制地区配送模板
     * @Author  wangzx
     * @Date   2019/7/26 9:40
     * @Param  [vsjSysDistTemplate]
     * provinceCode：配送地区 0=全国；provinceCode=vsj_sys_areas的省级code值
     * cityCode:对应vsj_sys_areas的市级code值
     * name：模板名称
     * mdoel：计费方式 0=按件计费；1=按重量计费
     * firstHeavy:model=0 首件；model = 1 首重
     * firstHeavyCost:model = 0 首件费用；mdoel = 1 首重费用
     * nextHeavy:model = 0 续xxx件；model = 1 续重 xxx
     * nextHeavyCost:model = 0 续件费用；model = 续重费用
     * @Return java.lang.Object
     * @Exception
     *
     */
    @PostMapping("insertDistTemplate")
    @ApiOperation(value = "自定义全国/定制地区配送模板", notes = "自定义全国/定制地区配送模板", httpMethod = "POST")
    public BaseResponseParam insertDistTemplate(@RequestBody VsjSysDistTemplateRequest vsjSysDistTemplateRequest){
        logger.info("自定义全国/定制定地区配送模板insertDistTemplate入参={}",vsjSysDistTemplateRequest);
        BaseResponseParam response = distTemplateService.insertDistTemplate(vsjSysDistTemplateRequest,getPlatformCode());
        logger.info("自定义全国/定制定地区配送模板insertDistTemplate出参={}",response);
        return response;
    }

    /**
     * @Description 获取配送模板列表
     * @Author  wangzx
     * @Date   2019/7/26 11:38
     * @Return java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getDistTemplateList")
    @ApiOperation(value = "获取获取配送模板列表", notes = "获取获取配送模板列表", httpMethod = "POST")
    public BaseResponseParam getDistTemplateList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("获取获取配送模板列表getDistTemplateList入参={}","无");
        BaseResponseParam response = distTemplateService.getDistTemplateList(queryStat);
        logger.info("获取获取配送模板列表getDistTemplateList出参={}",response);
        return response;
    }

    /**
     * @Description 获取配送模板详情
     * @Author  wangzx
     * @Date   2019/7/26 11:42
     * @Param  [queryStat]
     * id:配送模板id
     * @Return java.lang.Object
     * @Exception
     *
     */
    @PostMapping("getDistTemplateDetail")
    @ApiOperation(value = "获取获取配送模板详情", notes = "获取获取配送模板详情", httpMethod = "POST")
    public BaseResponseParam getDistTemplateDetail(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("获取获取配送模板详情getDistTemplateDetail入参={}",queryStat);
        BaseResponseParam response = distTemplateService.getDistTemplateDetail(queryStat);
        logger.info("获取获取配送模板详情getDistTemplateDetail出参={}",response);
        return response;
    }

    /**
     * @Description 修改配送模板
     * @Author  wangzx
     * @Date   2019/7/26 12:26
     * @Param  [vsjSysDistTemplate]
     * 参数同添加
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("updateDistTemplate")
    @ApiOperation(value = "修改配送模板", notes = "修改配送模板", httpMethod = "POST")
    public BaseResponseParam updateDistTemplate(@RequestBody VsjSysDistTemplateRequest vsjSysDistTemplateRequest){
        logger.info("修改配送模板updateDistTemplate入参={}",vsjSysDistTemplateRequest);
        BaseResponseParam response = distTemplateService.updateDistTemplate(vsjSysDistTemplateRequest,getPlatformCode());
        logger.info("修改配送模板updateDistTemplate出参={}",response);
        return response;
    }

    /**
     * @Description 删除配送模板
     * @Author  wangzx
     * @Date   2019/7/26 13:31
     * @Param  [queryStat]
     * id:配送模板id
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("deleteDistTemplate")
    @ApiOperation(value = "删除配送模板", notes = "删除配送模板", httpMethod = "POST")
    public BaseResponseParam deleteDistTemplate(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("删除配送模板deleteDistTemplate入参={}",queryStat);
        BaseResponseParam response = distTemplateService.deleteDistTemplate(queryStat);
        logger.info("删除配送模板deleteDistTemplate出参={}",response);
        return response;
    }

    /**
     * @Description 修改默认配送模板
     * @Author  wangzx
     * @Date   2019/8/19 9:13
     * @Param  [queryStat]
     * id:新的默认模板id
     * oldId:旧的默认模板id
     * @Return com.vsj.common.model.BaseResponseParam
     * @Exception
     */
    @ApiOperation(value = "修改默认配送模板", notes = "修改默认配送模板", httpMethod = "POST")
    @PostMapping("/updateDistDefault")
    public BaseResponseParam updateDistDefault(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("修改默认配送模板入参={}",queryStat);
        BaseResponseParam response = distTemplateService.updateDistDefault(queryStat);
        logger.info("修改默认配送模板出参={}",response);
        return response;
    }

    /**
     * @Description 批量添加商品规格
     * @Author  wangzx
     * @Date   2019/7/26 13:51
     * @Param  [productSpecsList]
     * @Return java.lang.Object
     * @Exception
     *
     */
    @PostMapping("insertProductSpecs")
    @ApiOperation(value = "批量添加商品规格", notes = "批量添加商品规格", httpMethod = "POST")
    public BaseResponseParam insertProductSpecs(@RequestBody  ProductSpecsList productSpecsList){
        productSpecsList.setPlatformCode(getPlatformCode());
        logger.info("批量添加商品规格insertProductSpecs入参={}",productSpecsList);
        BaseResponseParam response = productSpecsService.insertProductSpecs(productSpecsList);
        logger.info("批量添加商品规格insertProductSpecs出参={}",response);
        return response;
    }

    /**
     * @Description 修改商品指定类型的信息
     * @Author  wangzx
     * @Date   2019/7/26 15:06
     * @Param  [vsjProductSpecs]
     * id：商品规格表的id
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("updateProductSpecs")
    @ApiOperation(value = "修改商品指定类型的信息", notes = "修改商品指定类型的信息", httpMethod = "POST")
    public BaseResponseParam updateProductSpecs(@RequestBody ProductSpecsList productSpecsList){
        logger.info("修改商品指定类型的信息updateProductSpecs入参={}",productSpecsList);
        BaseResponseParam response = productSpecsService.updateProductSpecs(productSpecsList,getPlatformCode());
        logger.info("修改商品指定类型的信息updateProductSpecs出参={}",response);
        return response;
    }

    /**
     * @Description 删除商品信息表的信息
     * @Author  wangzx
     * @Date   2019/7/26 15:24
     * @Param  [queryStat]
     * id：商品信息表id
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @PostMapping("deleteProductSpecs")
    @ApiOperation(value = "删除商品规格等信息", notes = "删除商品规格等信息", httpMethod = "POST")
    public BaseResponseParam deleteProductSpecs(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("删除商品规格等信息deleteProductSpecs入参={}",queryStat);
        BaseResponseParam response = productSpecsService.deleteProductSpecs(queryStat);
        logger.info("删除商品规格等信息deleteProductSpecs出参={}",response);
        return response;
    }

    /**
     * @Description 添加商品分润管理
     * @Author  wangzx
     * @Date   2019/7/26 16:44
     * @Param  [vsjProduct, vsjStageSchema]
     * productId:商品id
     * isDealer:是否开启经销商提成开关  0=关  1=开
     * isCommission:是否开启独立提成  0=关  1=开
     * rankId:等级ID
     * openPeers:是否开启平级奖励  0=否 1=是
     * bonusNum:提成比例
     * bonusUnits:提成比例单位  0=百分比  1=元
     * peersNum:平级比例
     * peersHierarchy:平级层级
     * perrsUnits:平级提成比例单位  0=百分比  1=元
     * @Return java.lang.Object
     * @Exception
     *
     */
    @PostMapping("insertStageSchema")
    @ApiOperation(value = "添加商品分润管理", notes = "添加商品分润管理", httpMethod = "POST")
    public BaseResponseParam insertStageSchema(@RequestBody VsjStageSchemaRequestList vsjStageSchemaRequestList){
        logger.info("添加商品分润管理insertStageSchema入参={}",vsjStageSchemaRequestList);
        BaseResponseParam response = stageSchemaService.insertStageSchema(vsjStageSchemaRequestList,getPlatformCode());
        logger.info("添加商品分润管理insertStageSchema出参={}",response);
        return response;
    }

    /**
     * @Description 修改商品分润管理
     * @Author  wangzx
     * @Date   2019/7/26 16:44
     * @Param  [vsjProduct, vsjStageSchema]
     * productId:商品id
     * isDealer:是否开启经销商提成开关  0=关  1=开
     * isCommission:是否开启独立提成  0=关  1=开
     * rankId:等级ID
     * openPeers:是否开启平级奖励  0=否 1=是
     * bonusNum:提成比例
     * bonusUnits:提成比例单位  0=百分比  1=元
     * peersNum:平级比例
     * peersHierarchy:平级层级
     * perrsUnits:平级提成比例单位  0=百分比  1=元
     * @Return java.lang.Object
     * @Exception
     *
     */
    @PostMapping("updateStageSchema")
    @ApiOperation(value = "修改商品分润管理", notes = "修改商品分润管理", httpMethod = "POST")
    public BaseResponseParam updateStageSchema(@RequestBody VsjProductRequest vsjProductRequest, VsjStageSchemaRequest vsjStageSchemaRequest){
        logger.info("修改商品分润管理updateStageSchema："+vsjProductRequest+vsjStageSchemaRequest);
        BaseResponseParam response = stageSchemaService.updateStageSchema(vsjProductRequest,vsjStageSchemaRequest,getPlatformCode());
        logger.info("修改商品分润管理updateStageSchema："+response);
        return response;
    }


    /**
     * @Description 删除商品分润管理
     * @Author  wangzx
     * @Date   2019/7/26 17:57
     * @Param  [queryStat]
     * id:分润管理id
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @PostMapping("deleteStageSchema")
    @ApiOperation(value = "删除商品分润管理", notes = "删除商品分润管理", httpMethod = "POST")
    public BaseResponseParam deleteStageSchema(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("删除商品分润管理deleteStageSchema={}",queryStat);
        BaseResponseParam response = stageSchemaService.deleteStageSchema(queryStat);
        logger.info("删除商品分润管理deleteStageSchema={}",response);
        return response;
    }

    /**
     * @Description 查询商品极差模式配置
     * @Author  wangzx
     * @Date   2019/8/1 17:07
     * @Param  [queryStat]
     * productId：0=系统默认配置;其他为商品id
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询级差模式奖励", notes = "查询级差模式奖励", httpMethod = "POST")
    @PostMapping("getStageSchemaList")
    public BaseResponseParam getStageSchemaList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询级差模式奖励设置表入参={}", queryStat);
        BaseResponseParam response = stageSchemaService.getStageSchemaList(queryStat);
        logger.info("查询级差模式奖励设置表出参={}", response);
        return response;
    }

    /**
     * @Description 添加轮播图/推荐显示图片
     * @Author  wangzx
     * @Date   2019/7/29 9:23
     * @Param  [productImg]
     * productId:商品id
     * picDesc:图片描述
     * picUrl：图片地址，逗号分隔
     * isMaster：位置：0=轮播区域显示图  1=推荐区域显示图
     * picOrder:图片排序
     * picStatus:图片是否有效：0无效 1有效
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "添加轮播图/推荐显示图片", notes = "添加轮播图/推荐显示图片", httpMethod = "POST")
    @PostMapping("insertProductImage")
    public BaseResponseParam insertProductImage(@RequestBody ProductImgRequestList productImgRequestList) {
        logger.info("添加轮播图/推荐显示图片入参={}", productImgRequestList);
        BaseResponseParam response = productImgService.insertProductImage(productImgRequestList,getPlatformCode());
        logger.info("添加轮播图/推荐显示图片出参={}", response);
        return response;
    }

    /**
     * @Description 查询首页推荐/轮播图列表
     * @Author  wangzx
     * @Date   2019/7/29 10:03
     * @Param  [queryStat]
     * status:图片是否有效：0无效 1有效
     * type: 0=轮播区域显示图  1=推荐区域显示图
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询轮播图/推荐显示图片列表", notes = "查询轮播图/推荐显示图片列表", httpMethod = "POST")
    @PostMapping("getProcuctImageList")
    public BaseResponseParam getProcuctImageList(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询轮播图/推荐显示图片列表getProcuctImageList入参={}", queryStat);
        BaseResponseParam response = productImgService.getProductImageList(queryStat);
        logger.info("查询轮播图/推荐显示图片列表getProcuctImageList出参={}", response);
        return response;
    }


    /**
     * @Description 修改首页推荐图片/轮播图片
     * @Author  wangzx
     * @Date   2019/7/29 10:12
     * @Param  [vsjProductImg]
     * id:主键id
     * productId:商品id
     * picDesc:图片描述
     * picUrl：图片地址，逗号分隔
     * isMaster：位置：0=轮播区域显示图  1=推荐区域显示图
     * picOrder:图片排序
     * picStatus:图片是否有效：0无效 1有效
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "修改轮播图/推荐显示图片列表", notes = "修改轮播图/推荐显示图片列表", httpMethod = "POST")
    @PostMapping("updateProductImage")
    public BaseResponseParam updateProductImage(@RequestBody ProductImgRequestList productImgRequestList){
        logger.info("修改轮播图/推荐显示图片列表updateProductImage入参={}", productImgRequestList);
        BaseResponseParam response = productImgService.updateProductImage(productImgRequestList,getPlatformCode());
        logger.info("修改轮播图/推荐显示图片列表updateProductImage出参={}", response);
        return response;
    }

    /**
     * @Description 删除轮播/推荐图片设置
     * @Author  wangzx
     * @Date   2019/7/29 10:24
     * @Param  [queryStat]
     * id:主键id
     * @Return  java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "删除轮播/推荐图片设置", notes = "删除轮播/推荐图片设置", httpMethod = "POST")
    @PostMapping("deleteProductImage")
    public BaseResponseParam deleteProductImage(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("删除轮播/推荐图片设置deleteProductImage入参={}", queryStat);
        BaseResponseParam response = productImgService.deleteProductImage(queryStat);
        logger.info("删除轮播/推荐图片设置deleteProductImage出参={}", response);
        return response;
    }
/**
 * @Author: HuaS
 * @Date :2019/7/29 17:48
 * @Describe:增加一个商品分类
 */
    @ApiOperation(value = "增加商品分类", notes = "增加商品分类", httpMethod = "POST")
    @PostMapping("InsertProductCategory")
    public Object InsertProductCategory(@RequestBody ProductCategory productCategory){
        logger.info("增加分类InsertProductCategory入参={}" , JSON.toJSONString(productCategory) );
//      vsjProductCategory.setPlatformCode(getPlatformCode());
        Object obj = productCatetoryService.insertProductCategory(productCategory,getPlatformCode());
        logger.info("增加分类InsertProductCategory出参：" + obj);
        return obj;
    }

    @ApiOperation(value = "修改商品分类", notes = "修改商品分类", httpMethod = "POST")
    @PostMapping("updateProductCategory")
    public Object updateProductCategory(@RequestBody ProductCategory productCategory){
        logger.info("修改商品分类InsertProductCategory入参：" + productCategory);
        Object obj = productCatetoryService.updateProductCategory(productCategory,getPlatformCode());
        logger.info("修改商品分类InsertProductCategory出参：" + obj);
        return obj;
    }

    /**
     * @Author: HuaS
     * @Date :2019/7/29 20:00
     * @Describe:删除一个商品分类
     */
    @ApiOperation(value = "删除商品分类", notes = "删除商品分类", httpMethod = "POST")
    @PostMapping("deleteProductCategory")
    public Object deleteProductCategory(@RequestBody ProductCategory productCategory){
        logger.info("删除商品分类InsertProductCategory入参：" + productCategory);
        Object obj = productCatetoryService.deleteProductCategory(productCategory,getPlatformCode());
        logger.info("删除商品分类InsertProductCategory出参：" + obj);
        return obj;
    }
    /**
     * @Author: HuaS
     * @Date :2019/7/30 12:35
     * @Describe:根据查询分类列表
     */
//    @ApiOperation(value = "查询商品分类列表", notes = "查询商品分类列表", httpMethod = "POST")
//    @PostMapping("selectProductCategoryDetails")
//    public Object selectProductCategoryDetails(@RequestBody ProductCategory productCategory){
//        logger.info("根据id查询商品分类列表InsertProductCategory入参：" + productCategory);
//        Object obj = productCatetoryService.selectProductCategoryDetails(productCategory);
//        logger.info("根据id查询商品分类列表InsertProductCategory出参：" + obj);
//        return obj;
//    }

    /**
     * @Description 添加商品分销奖励
     * @Author  wangzx
     * @Date   2019/8/14 14:01
     * @Param  [vsjStageDistr]
     * @Return java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "添加商品分销奖励", notes = "添加商品分销奖励", httpMethod = "POST")
    @PostMapping("insertStageDistr")
    public BaseResponseParam insertStageDistr(@RequestBody VsjStageDistrRequest vsjStageDistrRequest){
        logger.info("添加商品分销奖励入参={}",vsjStageDistrRequest);
        BaseResponseParam response = vsjStageDistrService.insertStageDistr(vsjStageDistrRequest,getPlatformCode());
        logger.info("添加商品分销奖励出参={}",response);
        return response;
    }

    /**
     * @Description 查询商品分销奖励设置
     * @Author  wangzx
     * @Date   2019/8/14 14:33
     * @Param  [queryStat]
     * id:商品id
     * @Return java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "查询商品分销奖励设置", notes = "查询商品分销奖励设置", httpMethod = "POST")
    @PostMapping("getStageDistr")
    public BaseResponseParam getStageDistr(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("查询商品分销奖励设置入参={}",queryStat);
        BaseResponseParam response = vsjStageDistrService.getStageDistrByProductId(queryStat);
        logger.info("查询商品分销奖励设置出参={}",response);
        return response;
    }

    /**
     * @Description 修改分享奖励配置
     * @Author  wangzx
     * @Date   2019/8/14 14:43
     * @Param  [vsjStageDistrRequest]
     * @Return      java.lang.Object
     * @Exception
     */
    @ApiOperation(value = "修改商品分销奖励设置", notes = "修改商品分销奖励设置", httpMethod = "POST")
    @PostMapping("updateStageDistr")
    public BaseResponseParam updateStageDistr(@RequestBody VsjStageDistrRequest vsjStageDistrRequest){
        logger.info("修改商品分销奖励设置入参={}",vsjStageDistrRequest);
        BaseResponseParam response = vsjStageDistrService.updateStageDistrById(vsjStageDistrRequest,getPlatformCode());
        logger.info("修改商品分销奖励设置出参={}",response);
        return response;
    }

    /**
     * @Description 删除商品分销奖励模式配置
     * @Author  wangzx
     * @Date   2019/8/14 14:55
     * @Param  [queryStat]
     * id:分销模式配置id
     * @Return java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "删除商品分销奖励模式配置", notes = "删除商品分销奖励模式配置", httpMethod = "POST")
    @PostMapping("deleteStageDistr")
    public BaseResponseParam deleteStageDistr(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("删除商品分销奖励模式配置入参={}",queryStat);
        BaseResponseParam response = vsjStageDistrService.deleteStageDistrById(queryStat);
        logger.info("删除商品分销奖励模式配置出参={}",response);
        return response;
    }


    /**
     * @Description 测试获取配送模板配置
     * @Author  wangzx
     * @Date   2019/8/20 14:59
     * @Param  [queryStat]
     * id:商品关联的配送模板id
     * cityCode:用户地址市级code
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "获取商品配送模板配置", notes = "获取商品配送模板配置", httpMethod = "POST")
    @PostMapping("getProductDist")
    public BaseResponseParam getProductDist(QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("获取商品配送模板配置");
        ParentDist parentDist = distTemplateService.getCityDistConf(queryStat);
        return BaseResponseParam.success(parentDist);
    }

    @PostMapping("/getMasteImage")
    public BaseResponseParam getMasteImage(@RequestBody QueryStat queryStat){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("获取商品配送模板配置");
        BaseResponseParam responseParam = productImgService.getMasteImage(queryStat);
        logger.info("获取商品配送模板配置");
        return responseParam;
    }
}
