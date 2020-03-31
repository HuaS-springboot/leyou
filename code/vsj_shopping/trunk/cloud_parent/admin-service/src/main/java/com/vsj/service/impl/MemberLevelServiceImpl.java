package com.vsj.service.impl;


import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.constant.DefaultCodeConstant;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MemberLevel;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.MemberLevelDao;
import com.vsj.dao.VsjMemberProfitDAO;
import com.vsj.model.VsjMemberLevel;
import com.vsj.service.MemberLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/7/24 14:47
 * @Describe:
 */

@Service
public class MemberLevelServiceImpl implements MemberLevelService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberLevelDao memberLevelDao;

    @Autowired
    private AbstractObjectConverter<MemberLevel, VsjMemberLevel> convert;


    @Override
    public BaseResponseParam findAllLevel(Integer platformCode) {
        List<VsjMemberLevel> vsjMemberLevels = memberLevelDao.findAllLevel(platformCode);
        return BaseResponseParam.success(vsjMemberLevels);
    }

    @Override
    public BaseResponseParam deleteLevelById(Integer levelId, Integer platformCode) {
        if (memberLevelDao.deleteLevelById(levelId, platformCode) > 0) {
            return BaseResponseParam.success();
        } else {
            return BaseResponseParam.fail();
        }
    }

    @Override
    public BaseResponseParam updateLevel(MemberLevel memberLevel) {
        VsjMemberLevel vsjMemberLevel = null;
        vsjMemberLevel = convert.convert(memberLevel, VsjMemberLevel.class);
        if (null == vsjMemberLevel) {
            logger.debug("会员级别转换失败...");
            return BaseResponseParam.fail();
        }
        int result = -1;
        if (null != vsjMemberLevel.getId()) {
            logger.debug("id不为空,是修改...");
            result = memberLevelDao.updateLevel(vsjMemberLevel);
        } else {
            logger.debug("id为空时,是新增...");
            result = memberLevelDao.saveMemberLevel(vsjMemberLevel);
        }
        if (result > 0) {
            return BaseResponseParam.success();
        } else {
            return BaseResponseParam.fail();
        }
    }

    @Override
    public BaseResponseParam editDefaultLevel(MemberLevel memberLevel) {
        int result = 0;
        if (null == memberLevel.getOldId()) {
            result = 1;
        } else {
            //旧的先置为非默认
            logger.debug("修改旧默认值为非默认...oldId={}",memberLevel.getOldId());
            result = memberLevelDao.editDefaultLevel(memberLevel.getOldId(), memberLevel.getPlatformCode(), DefaultCodeConstant.NO_DEFAULT);
        }
        if (result > 0) {
            //新的改为默认
            logger.debug("修改新等级为默认...Id={}",memberLevel.getId());
            result = memberLevelDao.editDefaultLevel(memberLevel.getId(), memberLevel.getPlatformCode(), DefaultCodeConstant.DEFAULT);
        }
        return result > 0 ? BaseResponseParam.success() : BaseResponseParam.fail();
    }

}
