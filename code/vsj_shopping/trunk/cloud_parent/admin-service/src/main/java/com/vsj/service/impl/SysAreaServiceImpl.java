package com.vsj.service.impl;

import com.alibaba.fastjson.JSON;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.DistTemplateDAO;
import com.vsj.dao.SysAreaDAO;
import com.vsj.model.VsjSysAreas;
import com.vsj.model.VsjSysDistTemplate;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.service.SysAreaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SysAreaServiceImpl
 * @Description TODO
 * @Date 2019/7/25 15:18
 * @Created by wangzx
 */
@Service
public class SysAreaServiceImpl implements SysAreaService {

    @Autowired
    private SysAreaDAO sysAreaDao;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> queryStatConvert;


    @Override
    public BaseResponseParam getSysAreas(QueryStat queryStat) {
        BaseQueryStat baseQueryStat = queryStatConvert.convert(queryStat, BaseQueryStat.class);
        List<VsjSysAreas> list = sysAreaDao.getSysAreasList(baseQueryStat);
        return BaseResponseParam.success(list);
    }
}
