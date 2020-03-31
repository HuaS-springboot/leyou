package com.vsj.common.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;

/**
 * @Classname MemberService
 * @Description 会员相关
 * @Date 2019/8/27 9:06
 * @Created by wangzx
 */
public interface MemberService {

    /**
     * 处理会员上下级关系
     * @param queryStat
     * @return
     */
    BaseResponseParam disposeRelation(QueryStat queryStat);
}
