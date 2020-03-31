package com.vsj.service;

import com.vsj.common.model.request.VsjMemberRelation;
import com.vsj.model.VsjMemberRelationSet;
/**
 * @Author: HuaS
 * @Date :2019/7/25 20:09
 * @Describe:
 */
public interface MemberRelationService {
    Object updateMemberRelation(VsjMemberRelation vsjMemberRelation);
    Object getMemberRelation(Integer platformCode);
}
