package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecParamService {

    @Autowired
    private SpecParamMapper specParamMapper;
    @Autowired
    private SpecGroupMapper specGroupMapper;

    public List<SpecParam> querySpecParamsList(Long gid,Long cid,Boolean searching) {
        SpecParam specParams = new SpecParam();
        specParams.setGroupId(gid);
        specParams.setCid(cid);
        specParams.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(specParams);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FIND);
        }
        return list;

    }


    public List<SpecGroup> queryLBistyCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> groups = specGroupMapper.select(specGroup);
        if(CollectionUtils.isEmpty(groups)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FIND);
        }
        List<SpecParam> specParams = querySpecParamsList(null, cid, false);
        //先把规格参数变成map，map的key是规格参数组的id，map的值是足下的所有的值
        Map<Long,List<SpecParam>> map = new HashMap<>();
        for(SpecParam param :specParams){
            if(!map.containsKey(param.getGroupId())){
                map.put(param.getGroupId(),new ArrayList<>());
            }
            map.get(param.getGroupId()).add(param);
        }

        //填充param到group
        for(SpecGroup specGroup1 : groups){
            specGroup1.setParams(map.get(specGroup1.getId()));
        }
        return groups;
    }














}
