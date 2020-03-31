package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;

/**
 * @Classname SysAreaService
 * @Description TODO
 * @Date 2019/7/25 15:18
 * @Created by wangzx
 */
public interface SysAreaService {

    BaseResponseParam getSysAreas(QueryStat queryStat);

}
