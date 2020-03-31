package com.vsj.material.service.impl;

import com.vsj.material.dao.VsjMaterialMemberDAO;
import com.vsj.material.model.VsjMaterialMember;
import com.vsj.material.service.VsjMaterialMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname VsjMaterialMemberServiceImpl
 * @Description 回原形相关实现类
 * @Date 2019/8/14 16:52
 * @Created by wangzx
 */
@Service
public class VsjMaterialMemberServiceImpl implements VsjMaterialMemberService {

    @Autowired
    private VsjMaterialMemberDAO vsjMaterialMemberDAO;

    @Override
    public int updateMaterialMember(VsjMaterialMember vsjMaterialMember) {
        return vsjMaterialMemberDAO.updateMaterialMember(vsjMaterialMember);
    }
}
