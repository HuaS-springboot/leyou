package com.vsj.material.service;

import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.SysConfigList;

/**
 * @Classname SystemConfigService
 * @Description 系统配置接口
 * @Date 2019/8/13 10:28
 * @Created by wangzx
 */
public interface SystemConfigService {

    Object getSysConfigList(QueryStat queryStat);

    Object updateSysConfig(SysConfigList sysConfigList,Integer platformCode);

    Object bindPhone(QueryStat querySset);
}
