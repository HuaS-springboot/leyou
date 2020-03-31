package com.vsj.material.service.impl;


import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.UUIDGeneratorUtils;
import com.vsj.material.dao.VsjMaterialSysUserDao;
import com.vsj.material.dao.VsjSysDictionaryDAO;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.UserLoginModel;
import com.vsj.material.service.VsjMaterialSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VsjMaterialSysUserServiceImpl implements VsjMaterialSysUserService {
    @Autowired
    private RedisClient redisClient;

    @Autowired
    private VsjMaterialSysUserDao vsjMaterialSysUserDao;
    @Autowired
    private VsjSysDictionaryDAO vsjSysDictionaryDAO;

    @Override
    public Object loginSysUser(QueryStat querySet) {
        UserLoginModel userLoginModel=vsjMaterialSysUserDao.loginSysUser(querySet);
        if(userLoginModel == null){
            return BaseResponseParam .fail();
        }
        String token= UUIDGeneratorUtils.getUUIDLower();
        userLoginModel.setToken(token);
        redisClient.set(RedisKeyConstant.USER_TOKEN+userLoginModel.getId(),token,1L, TimeUnit.DAYS);
        userLoginModel.setSysDictionary(vsjSysDictionaryDAO.selectSysDictionary());

        return BaseResponseParam.success(userLoginModel);
    }
}
