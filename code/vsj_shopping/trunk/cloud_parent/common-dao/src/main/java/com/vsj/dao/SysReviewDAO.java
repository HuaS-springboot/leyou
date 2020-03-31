package com.vsj.dao;

import com.vsj.mapper.SysReviewMapper;
import com.vsj.model.SysReview;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.SysReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname SysReviewDAO
 * @Description 审核DAO
 * @Date 2019/8/21 15:07
 * @Created by wangzx
 */
@Component
public class SysReviewDAO {

    @Autowired
    private SysReviewMapper sysReviewMapper;

    public List<SysReviewResponse> getReviewList(BaseQueryStat baseQueryStat) {
        return sysReviewMapper.getReviewList(baseQueryStat);
    }

    public int updateSysReview(SysReview sysReview) {
        return sysReviewMapper.updateSysReview(sysReview);
    }

    public SysReview getReviewById(Integer id, Integer platformCode) {
        return sysReviewMapper.getReviewById(id,platformCode);
    }
}
