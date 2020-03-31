package com.vsj.material.dao;

import com.vsj.material.mapper.MaterialConversionMapper;
import com.vsj.material.model.MaterialConversion;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.MaterialConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname MaterialConversionDAO
 * @Description 兑换码DAO
 * @Date 2019/8/16 10:50
 * @Created by wangzx
 */
@Component
public class MaterialConversionDAO {


    @Autowired
    private MaterialConversionMapper materialConversionMapper;

    public int insertMaterialConversion(MaterialConversion materialConversion) {
        return materialConversionMapper.insertMaterialConversion(materialConversion);
    }

    public List<MaterialConversionResponse> getMaterialConversionList(QueryStat queryStat) {
        return materialConversionMapper.getMaterialConversionList(queryStat);
    }


    public MaterialConversion getConversionByCode(QueryStat queryStat){
        return materialConversionMapper.getConversionByCode(queryStat);
    }




    public int deleteMaterialConversion(QueryStat queryStat) {
        return materialConversionMapper.deleteMaterialConversion(queryStat);
    }

}
