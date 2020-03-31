package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.MemberLevel;

/**
 * @Author: HuaS
 * @Date :2019/7/24 14:51
 * @Describe:
 */
public interface MemberLevelService {

    BaseResponseParam findAllLevel(Integer platformCode);
    BaseResponseParam deleteLevelById(Integer levelId,Integer platformCode);
    BaseResponseParam updateLevel (MemberLevel memberLevel);
    BaseResponseParam editDefaultLevel(MemberLevel memberLevel);
}
