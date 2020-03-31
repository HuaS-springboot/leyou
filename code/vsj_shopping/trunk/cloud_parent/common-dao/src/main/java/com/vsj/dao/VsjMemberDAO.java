package com.vsj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vsj.mapper.VsjMemberMapper;
import com.vsj.model.VsjMember;

import java.util.List;

@Component
public class VsjMemberDAO {
    @Autowired
    private VsjMemberMapper vsjMemberMapper;

    public VsjMember selectByPrimaryKey(Integer id) {
        return vsjMemberMapper.selectByPrimaryKey(id);
    }


    public int updateVsjMember(VsjMember vsjMember) {
        return vsjMemberMapper.updateVsjMember(vsjMember);
    }

    public VsjMember selectByRegId(Integer regId) {
        return vsjMemberMapper.selectByRegId(regId);
    }

    public List<Integer> getTotalSuperMemberIds(Integer id) {
        return vsjMemberMapper.getTotalSuperMemberIds(id);
    }
}
