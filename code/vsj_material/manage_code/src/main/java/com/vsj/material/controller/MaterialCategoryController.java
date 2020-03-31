package com.vsj.material.controller;

import com.alibaba.fastjson.JSON;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.model.request.MaterialCategory;
import com.vsj.material.service.VsjMaterialCategoryService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/admin/materialCategory",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaterialCategoryController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjMaterialCategoryService vsjMaterialCategoryService;

    /**
     * @Description：
     * @Author HuaS
     * @Date   2019/8/23 16:09
     * @Param  [MaterialCategory]
     * id :素材id
     * cateName：分类名称
     * parentId：素材父id
     * sort：素材权重
     * status：状态
     * createTime：创建时间
     * paltformCode：平台code码
     * children：子分类素材
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "增加分类素材", notes = "增加分类素材", httpMethod = "POST")
    @PostMapping("insertMaterialCategory")
    public Object insertMaterialCategory(@RequestBody MaterialCategory materialCategory){
        logger.info("增加分类insertMaterialCategory入参={}" , JSON.toJSONString(materialCategory) );
        Object obj = vsjMaterialCategoryService.insertMaterialCategory(materialCategory,getPlatformCode());
        logger.info("增加分类insertMaterialCategory出参：" + obj);
        return obj;
    }
    /**
     * @Description：查询素材一二三级分类
     * @Author HuaS
     * @Date   2019/8/23 16:09
     * @Param   getPlatformCode:平台code码
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "查询分类素材", notes = "查询分类素材", httpMethod = "POST")
    @PostMapping("findMaterialCategory")
    public BaseResponseParam findMaterialCategory(){
        logger.info("查询素材一二三级分类入参：" );
        BaseResponseParam response = vsjMaterialCategoryService.findMaterialCategory(getPlatformCode());
        logger.info("查询素材一二三级分类出参："+response);
        return response;
    }
    /**
     * @Description：删除素材一二三级分类
     * @Author HuaS
     * @Date   2019/8/23 16:09
     * @Param [MaterialCategory]
     * @Return id:每一层id
     * @Exception
     *
     */
    @ApiOperation(value = "删除分类素材", notes = "删除分类素材", httpMethod = "POST")
    @PostMapping("deleteMaterialCategory")
    public Object deleteMaterialCategory(@RequestBody MaterialCategory materialCategory){
        logger.info("删除素材一二三级分类入参：" + materialCategory);
        Object obj = vsjMaterialCategoryService.deleteMaterialCategory(materialCategory,getPlatformCode());
        logger.info("删除素材一二三级分类出参："+obj);
        return obj;
    }
    /**
     * @Description：修改素材一二三级分类
     * @Author HuaS
     * @Date   2019/8/23 16:09
     * @Param   [MaterialCategory]
     * id:修改id
     * id :素材id
     * cateName：分类名称
     * sort：素材权重
     * status：状态
     * createTime：创建时间
     * @Return
     * @Exception
     *
     */
    @ApiOperation(value = "修改分类素材", notes = "修改分类素材", httpMethod = "POST")

    @PostMapping("editMaterialCategroy")
    public Object editMaterialCategroy(@RequestBody MaterialCategory materialCategory){
        logger.info("修改素材一二三级分类入参：" + materialCategory);
        Object obj = vsjMaterialCategoryService.editMaterialCategroy(materialCategory,getPlatformCode());
        logger.info("修改素材一二三级分类出参："+obj);
        return obj;
    }

}
