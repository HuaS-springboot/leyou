package com.vsj.material.service.impl;

import cn.gjing.AliOss;
import com.vsj.common.model.BaseResponseParam;


import com.vsj.material.constants.SysConfigConstants;
import com.vsj.material.dao.VsjMaterialSysConfigDAO;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname UploadServiceImpl
 * @Description OSS 上传实现
 * @Date 2019/8/27 11:38
 * @Created by sxm
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private VsjMaterialSysConfigDAO vsjMaterialSysConfigDAO;

    @Override
    public BaseResponseParam uploadFile(QueryStat queryStat, MultipartFile file) {
        if (file == null || queryStat.getPlatformCode() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        String endpoint = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.OSS_ENDPOINT,queryStat.getPlatformCode()).getValue();
        // 在该OSS的同一地区ESC上可使用内网地址,加快传输速度
        // String endpoint = "oss-cn-qingdao-internal.aliyuncs.com";
        String accesskey = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.ACCESS_KEY_ID,queryStat.getPlatformCode()).getValue();
        String secret = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.ACCESS_KEY_SECRET,queryStat.getPlatformCode()).getValue();
        String bucketName = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.BUCKET_NAME,queryStat.getPlatformCode()).getValue();
        String url = AliOss.of(endpoint, accesskey, secret, bucketName).upload(file);
        Map<String, String> result = new HashMap<>();
        result.put("url",url);
        return BaseResponseParam.success(result);
    }

    @Override
    public String downloadFile(String localPath,String fileName, Integer platformCode) {
        String endpoint = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.OSS_ENDPOINT,platformCode).getValue();
        String accesskey = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.ACCESS_KEY_ID,platformCode).getValue();
        String secret = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.ACCESS_KEY_SECRET,platformCode).getValue();
        String bucketName = vsjMaterialSysConfigDAO.selectByConfigName(SysConfigConstants.BUCKET_NAME,platformCode).getValue();
        boolean b = AliOss.of(endpoint, accesskey,secret, bucketName).downloadFile(fileName, localPath);
        if(b){
            String localFileName = fileName.substring(fileName.indexOf(endpoint) + endpoint.length() + 1);
            return localPath + localFileName;
        }
        return null;
    }
}
