package com.vsj.dao;

import com.vsj.mapper.StageSchemaMapper;
import com.vsj.model.VsjStageSchema;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.StageSchemaLevelInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname StageSchemaDao
 * @Description 奖励模式设置Dao
 * @Date 2019/7/29 11:23
 * @Created by wangzx
 */
@Component
public class StageSchemaDAO {

    @Autowired
    private StageSchemaMapper stageSchemaMapper;

    public int insertStageSchema(VsjStageSchema vsjStageSchema) {
        return stageSchemaMapper.insertStageSchema(vsjStageSchema);
    }

    public int updateStageSchema(VsjStageSchema vsjStageSchema) {
        return stageSchemaMapper.updateStageSchema(vsjStageSchema);
    }

    public int deleteStageSchema(BaseQueryStat queryStat) {
        return stageSchemaMapper.deleteStageSchema(queryStat);
    }
    
    public List<VsjStageSchema> selectStageSchema(Integer productId,Integer platformCode) {
    	List<VsjStageSchema> vsjStageSchemaList = stageSchemaMapper.selectStageSchema(productId, platformCode);
        return vsjStageSchemaList;
    }
    
    public List<VsjStageSchema> selectDefaultStageSchema(Integer platformCode) {
    	return stageSchemaMapper.selectStageSchema(0, platformCode);
    }
    
    public List<StageSchemaLevelInfo> selectHighLevelAndSchema(Integer levelId,Integer productId,Integer platformCode) {
    	List<StageSchemaLevelInfo> vsjStageSchemaList = stageSchemaMapper.selectHighLevelAndSchema(levelId,productId, platformCode);
        return vsjStageSchemaList;
    }
    
    public List<StageSchemaLevelInfo> selectHighLevelAndDefaultSchema(Integer levelId,Integer platformCode) {
    	return stageSchemaMapper.selectHighLevelAndSchema(levelId,0, platformCode);
    }

    public void deleteStageSchemaByProductId(BaseQueryStat baseQueryStat) {
        stageSchemaMapper.deleteStageSchemaByProductId(baseQueryStat);
    }

    public void insertStageSchemaBatch(List<VsjStageSchema> list) {
        stageSchemaMapper.insertStageSchemaBatch(list);
    }
}
