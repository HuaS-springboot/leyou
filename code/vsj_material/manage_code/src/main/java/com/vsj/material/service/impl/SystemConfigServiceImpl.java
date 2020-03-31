package com.vsj.material.service.impl;

import com.vsj.common.helper.SendMessageHelper;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.VerificationCodeModel;
import com.vsj.common.utils.StringUtil;
import com.vsj.material.constants.SysConfigClassifyConstants;
import com.vsj.material.constants.SysConfigConstants;
import com.vsj.material.dao.VsjMaterialSysConfigDAO;
import com.vsj.material.model.VsjMaterialSysConfig;
import com.vsj.material.model.convert.AbstractObjectConverter;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.SysConfig;
import com.vsj.material.model.request.SysConfigList;
import com.vsj.material.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname SystemConfigServiceImpl
 * @Description 系统配置实现类
 * @Date 2019/8/13 10:28
 * @Created by wangzx
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VsjMaterialSysConfigDAO vsjMaterialSysConfigDAO;
    @Autowired
    private AbstractObjectConverter<SysConfig,VsjMaterialSysConfig> convert;

    @Autowired
    private SendMessageHelper sendMessageHelper;





    @Override
    public Object getSysConfigList(QueryStat queryStat) {
        if(queryStat == null || queryStat.getType() == null || queryStat.getPlatformCode() == null){
            return BaseResponse.fail("参数错误");
        }
       List<VsjMaterialSysConfig> results = null;
        if(queryStat.getType() == SysConfigClassifyConstants.SHOPPING_MOUNT){
            logger.debug("开始查询商城装修配置...");
            String keys[] = SysConfigConstants.SHOPPING_GROUP;
            results = selectByKeys(keys, queryStat.getPlatformCode());
        }else if(queryStat.getType() == SysConfigClassifyConstants.SYSTEM){
            logger.debug("开始查询系统参数配置...");
            String keys[] = SysConfigConstants.SYS_GROUP;
            results = selectByKeys(keys, queryStat.getPlatformCode());
        }else if(queryStat.getType() == SysConfigClassifyConstants.PAY_PARAMETER){
            logger.debug("开始查询支付参数配置...");
            String keys[] = SysConfigConstants.WX_PAY_GROUP;
            results = selectByKeys(keys, queryStat.getPlatformCode());
        }else if(queryStat.getType() == SysConfigClassifyConstants.OTHER_API){
            logger.debug("开始查询第三方API参数配置...");
            String keys[] = SysConfigConstants.OTHER_API;
            results = selectByKeys(keys,queryStat.getPlatformCode());
        }else if(queryStat.getType() == SysConfigClassifyConstants.LEVEL_PERMISSION){
            logger.debug("开始查询会员等级权限...");
            String keys[] = SysConfigConstants.LEVEL_PERMISSION;
            results = selectByKeys(keys, queryStat.getPlatformCode());
        }
        if(StringUtil.isEmptyList(results)){
            return BaseResponseParam.fail("未查询到数据");
        }
        return BaseResponseParam.success(results);
    }

    @Override
    public Object updateSysConfig(SysConfigList sysConfigList,Integer platformCode) {
        List<VsjMaterialSysConfig> materialSysConfigList = new ArrayList<>();
        List<SysConfig> configList = sysConfigList.getSysConfigList();
        if(configList.isEmpty() || platformCode == null){
            return BaseResponse.fail("参数错误");
        }
        for (SysConfig sysConfig : configList) {
            VsjMaterialSysConfig vsjMaterialSysConfig = convert.convert(sysConfig, VsjMaterialSysConfig.class);
            vsjMaterialSysConfig.setPlatformCode(platformCode);
            materialSysConfigList.add(vsjMaterialSysConfig);
        }
        vsjMaterialSysConfigDAO.updateSysConfig(materialSysConfigList,platformCode);
        return BaseResponse.success();
    }


    private List<VsjMaterialSysConfig> selectByKeys(String[] keys, Integer platformCode) {
        List<VsjMaterialSysConfig> results = new ArrayList<>();
        VsjMaterialSysConfig vsjSysConfig = null;
        for(String key :keys){
            vsjSysConfig = vsjMaterialSysConfigDAO.selectByConfigName(key, platformCode);
            if(null != vsjSysConfig){
                results.add(vsjSysConfig);
            }
        }
        return results;
    }

    @Override
    public Object bindPhone(QueryStat querySet) {
        //系统配置key数组
        String[] keys = {"sdhjfgdf","other_ali_message_accesskeyid",
                "other_send_message_no","other_message_signature"};
        //根据key数组获取系统配置value值
        List<VsjMaterialSysConfig> list = selectByKeys(keys,querySet.getPlatformCode());
        //将所有查询出的系统配置的value值放入values集合中
        List<String> values = new ArrayList<>();
       /* list.stream().forEach(g ->{
           String s= g.getValue();
            values.add(s);
        });*/
        for(VsjMaterialSysConfig vsjMaterialSysConfig : list ){
         values.add(vsjMaterialSysConfig.getValue());
        }

        String jsonCode = VerificationCodeModel.json(StringUtil.getVerificationCode());
        boolean bool = sendMessageHelper.send(querySet.getPhone(),values.get(0),values.get(3),jsonCode,values.get(1),values.get(2));
        if(!bool){
            return BaseResponseParam.fail("参数问题");
        }
        return BaseResponseParam.success(jsonCode);
    }


}
