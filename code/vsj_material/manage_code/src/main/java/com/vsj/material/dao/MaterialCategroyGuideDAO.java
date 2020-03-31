package com.vsj.material.dao;

import com.vsj.material.mapper.MaterialCategroyGuideMapper;
import com.vsj.material.model.MaterialCategroyGuide;
import com.vsj.material.model.request.QueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname MaterialCategroyGuideDAO
 * @Description 分类导航DAO
 * @Date 2019/8/16 17:49
 * @Created by sxm
 */
@Component
public class MaterialCategroyGuideDAO {

    @Autowired
    private MaterialCategroyGuideMapper materialCategroyGuideMapper;

    public List<MaterialCategroyGuide> getMaterialCategroyGuide(QueryStat queryStat){
        return materialCategroyGuideMapper.getMaterialCategroyGuide(queryStat);
    }

    public boolean deleteMaterialCategroyGuide(QueryStat queryStat){
       int count = materialCategroyGuideMapper.deleteMaterialCategroyGuide(queryStat);
       if(count>0){
           return true;
       }
        return false;
    }
    public boolean updateMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide){
        int count = materialCategroyGuideMapper.updateMaterialCategroyGuide(materialCategroyGuide);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean insertMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide){
        int count = materialCategroyGuideMapper.insertMaterialCategroyGuide(materialCategroyGuide);
        if(count>0){
            return true;
        }
        return false;
    }

}
