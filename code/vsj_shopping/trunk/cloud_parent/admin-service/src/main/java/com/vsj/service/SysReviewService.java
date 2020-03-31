package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname SysReviewService
 * @Description 审核相关
 * @Date 2019/8/21 15:06
 * @Created by wangzx
 */
public interface SysReviewService {

    BaseResponseParam getReviewList(QueryStat queryStat);

    BaseResponseParam updateReview(QueryStat queryStat, HttpServletRequest request);
}
