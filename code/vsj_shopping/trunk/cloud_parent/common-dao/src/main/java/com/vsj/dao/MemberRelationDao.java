package com.vsj.dao;

import com.vsj.mapper.MemberRelationMapper;
import com.vsj.model.VsjMemberRelationSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberRelationDao {

    @Autowired
    private MemberRelationMapper memberRelationMapper;

    public int saveMemberRelation(VsjMemberRelationSet vsjSysRelationSet){
        return memberRelationMapper.saveMemberRelation(vsjSysRelationSet);
    }

    public int updateMemberRelation(VsjMemberRelationSet vsjSysRelationSet){
        return memberRelationMapper.updateMemberRelation(vsjSysRelationSet);
    }

    public VsjMemberRelationSet getMemberRelation(Integer platformCode){
        return memberRelationMapper.getMemberRelation(platformCode);
    }

}
