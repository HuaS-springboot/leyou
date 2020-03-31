package com.vsj.material.dao;

import com.vsj.material.mapper.VsjMaterialMemberMapper;
import com.vsj.material.model.VsjMaterialMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjMaterialMemberDAO
 * @Description 会员相关DAO
 * @Date 2019/8/14 16:53
 * @Created by wangzx
 */
@Component
public class VsjMaterialMemberDAO {

    @Autowired
    private VsjMaterialMemberMapper vsjMaterialMemberMapper;

    public int updateMaterialMember(VsjMaterialMember vsjMaterialMember) {
        return vsjMaterialMemberMapper.updateMaterialMember(vsjMaterialMember);
    }
}
