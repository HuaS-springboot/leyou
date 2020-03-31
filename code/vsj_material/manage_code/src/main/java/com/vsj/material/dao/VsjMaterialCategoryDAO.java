package com.vsj.material.dao;

import com.alibaba.fastjson.JSON;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.StringUtil;
import com.vsj.material.mapper.MaterialCategoryMapper;
import com.vsj.material.mapper.VsjMaterialMapper;
import com.vsj.material.mapper.VsjMaterialSysConfigMapper;
import com.vsj.material.model.VsjMaterial;
import com.vsj.material.model.VsjMaterialCategory;
import com.vsj.material.model.request.QueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname VsjMaterialCategoryDAO
 * @Description 分类DAO
 * @Date 2019/8/13 16:59
 * @Created by wangzx
 */
@Component
public class VsjMaterialCategoryDAO {

    @Autowired
    private VsjMaterialSysConfigMapper vsjMaterialSysConfigMapper;
    @Autowired
    private RedisClient redisClient;

    @Autowired
    public MaterialCategoryMapper materialCategoryMapper;


    public Object getMaterialCategory(Integer platformCode) {
        // 获取redis中的数据
        String redisStr = (String) redisClient.get(RedisKeyConstant.VSJ_MATERIAL_CATEGORY_KEY + platformCode);
        if (!StringUtil.isEmptyStr(redisStr)) {
            List<VsjMaterialCategory> rootList = JSON.parseArray(redisStr, VsjMaterialCategory.class);
            return rootList;
        }
        // 数据库的数据
        List<VsjMaterialCategory> rootList = getDbCategoryList(platformCode);
        // 数据放到redis中
        redisClient.set(RedisKeyConstant.VSJ_MATERIAL_CATEGORY_KEY + platformCode, JSON.toJSONString(rootList));
        return rootList;
    }

    private List<VsjMaterialCategory> getDbCategoryList(Integer platformCode) {
        // 保存结构
        List<VsjMaterialCategory> rootList = new ArrayList<>();
        // 数据库数据
        List<VsjMaterialCategory> dbCateList = vsjMaterialSysConfigMapper.getCategoryList(platformCode);
        // 保存到map中
        Map<Integer, VsjMaterialCategory> permissionMap = new HashMap<>();
        for (VsjMaterialCategory category : dbCateList) {
            VsjMaterialCategory child = category;
            permissionMap.put(category.getId(), category);
            if (0 == category.getParentId()) {
                // 获取root
                rootList.add(category);
            } else {
                // 根据child的parentCode当做key获取父级
                VsjMaterialCategory parent = permissionMap.get(child.getParentId());
                if (parent != null) {
                    parent.getChildren().add(child);
                }
            }
        }
        return rootList;
    }

    public int insertMaterialCategory(VsjMaterialCategory vsjMaterialCategory){
        return materialCategoryMapper.insertMaterialCategory(vsjMaterialCategory);
    }

    public int editMaterialCategroy(VsjMaterialCategory vsjMaterialCategory){
        return materialCategoryMapper.editMaterialCategroy(vsjMaterialCategory);
    }

    public int deleteMaterialCategory(VsjMaterialCategory vsjMaterialCategory){
        return materialCategoryMapper.deleteMaterialCategory(vsjMaterialCategory);
    }

    public List<VsjMaterialCategory> findMaterialCategory(Integer platformCode){
        List<VsjMaterialCategory> rootlist = new ArrayList<>();
        List<VsjMaterialCategory> dbCategoryList = materialCategoryMapper.findMaterialCategory(platformCode);
        //
        Map<Integer,VsjMaterialCategory> permissionMap = new HashMap<>();
        for(VsjMaterialCategory dbCategory:dbCategoryList){
            VsjMaterialCategory child = dbCategory;
            permissionMap.put(dbCategory.getId(),dbCategory);
            if(0 == dbCategory.getParentId()){
                rootlist.add(dbCategory);
            }else{
                VsjMaterialCategory parent = permissionMap.get(child.getParentId());//
                if(parent != null){
                    parent.getChildren().add(child);
                }
            }

        }
        return rootlist;
    }

    public List<VsjMaterialCategory> findMaterialCategoryOne(VsjMaterialCategory vsjMaterialCategory){
        return materialCategoryMapper.findMaterialCategoryOne(vsjMaterialCategory);
    }


}
