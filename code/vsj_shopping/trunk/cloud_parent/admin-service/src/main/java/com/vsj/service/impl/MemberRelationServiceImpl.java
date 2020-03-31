package com.vsj.service.impl;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.SysConfig;
import com.vsj.common.model.request.VsjMemberRelation;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.MemberRelationDao;
import com.vsj.dao.VsjRegisterDAO;
import com.vsj.mapper.MemberRelationMapper;
import com.vsj.model.VsjMemberRelationSet;
import com.vsj.model.VsjSysConfig;
import com.vsj.service.MemberRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/7/25 20:09
 * @Describe:
 */
@Service
public class MemberRelationServiceImpl implements MemberRelationService {
//    @Autowired
//    private MemberRelationMapper memberRelationMapper;
private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberRelationDao memberRelationDao;

    @Autowired
    private AbstractObjectConverter<VsjMemberRelation,VsjMemberRelationSet> convert;

    @Override
    public Object updateMemberRelation(VsjMemberRelation vsjMemberRelation) {
        VsjMemberRelationSet vsjMemberRelationSet = null;
        //格式转换
        vsjMemberRelationSet = convert.convert(vsjMemberRelation, VsjMemberRelationSet.class);

        if(null==vsjMemberRelationSet){
            logger.debug("会员关系转换失败updateMemberRelation...");
            return BaseResponseParam.fail();
        }
        int count = -1;
        if(null == vsjMemberRelationSet.getId()){
            logger.debug("会员关系Id传入为空,操作为新增．．．");
            //id为空,是新增
            count = memberRelationDao.saveMemberRelation(vsjMemberRelationSet);
        }else{
            //id不为空,是修改
            logger.debug("会员关系Id传入为空,操作为修改．．．");
            count = memberRelationDao.updateMemberRelation(vsjMemberRelationSet);
        }
        if(count > 0){
            return BaseResponseParam.success();
        }else{
            return BaseResponseParam.fail();
        }
    }


    @Override
    public Object getMemberRelation(Integer platformCode) {
        VsjMemberRelationSet vsjSysRelationSet = memberRelationDao.getMemberRelation(platformCode);
        return BaseResponseParam.success(vsjSysRelationSet);
    }
}
