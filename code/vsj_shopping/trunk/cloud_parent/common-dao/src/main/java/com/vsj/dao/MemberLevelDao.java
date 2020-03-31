package com.vsj.dao;

import com.vsj.mapper.MemberLevelMapper;
import com.vsj.model.VsjMemberLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberLevelDao {
    @Autowired
    private MemberLevelMapper memberLevelMapper;

    public int saveMemberLevel(VsjMemberLevel vsjMemberLevel) {
        return memberLevelMapper.saveMemberLevel(vsjMemberLevel);
    }

    public List<VsjMemberLevel> findAllLevel(Integer platformCode) {
        return memberLevelMapper.findAllLevel(platformCode);
    }

    public int deleteLevelById(Integer levelId, Integer platformCode) {
        return memberLevelMapper.deleteLevelById(levelId, platformCode);
    }

    public int updateLevel(VsjMemberLevel vsjMemberLevel) {
        return memberLevelMapper.updateLevel(vsjMemberLevel);
    }

    public VsjMemberLevel getLevelBySort(int sort, Integer platformCode) {
        return memberLevelMapper.getLevelBySort(sort, platformCode);
    }

    public int editDefaultLevel(int id, int platformCode, int isDefault) {
        return memberLevelMapper.editDefaultLevel(id, platformCode, isDefault);
    }
}
