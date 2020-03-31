package com.vsj.dao;

import com.vsj.mapper.DistTemplateMapper;
import com.vsj.model.VsjSysDistTemplate;

import com.vsj.model.request.BaseQueryStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname DistTemplateDAO
 * @Description TODO
 * @Date 2019/7/31 9:43
 * @Created by wangzx
 */
@Component
public class DistTemplateDAO {

    @Autowired
    private DistTemplateMapper distTemplateMapper;

    public List<VsjSysDistTemplate> getDistTemplateList(BaseQueryStat queryStat) {
        return distTemplateMapper.getDistTemplateList(queryStat);
    }

    public int insertDistTemplate(VsjSysDistTemplate vsjSysDistTemplate){
        return distTemplateMapper.insertDistTemplate(vsjSysDistTemplate);
    }

    public VsjSysDistTemplate getDistTemplateDetail(BaseQueryStat queryStat){
        return distTemplateMapper.getDistTemplateDetail(queryStat);
    }

    public int updateDistTemplate(VsjSysDistTemplate vsjSysDistTemplate){
        return distTemplateMapper.updateDistTemplate(vsjSysDistTemplate);
    }

    public int deleteDistTemplate(BaseQueryStat queryStat){
        return distTemplateMapper.deleteDistTemplate(queryStat);
    }

    public int getDistCount(Integer platformCode) {
        return distTemplateMapper.getDistCount(platformCode);
    }
}
