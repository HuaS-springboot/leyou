package com.vsj.service.impl;

import cn.gjing.AliOss;
import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.utils.OSSUtils;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.VsjSysConfigDAO;
import com.vsj.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname UploadServiceImpl
 * @Description OSS 上传实现
 * @Date 2019/7/30 11:42
 * @Created by wangzx
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private VsjSysConfigDAO vsjSysConfigDAO;

    @Override
    public BaseResponseParam uploadFile(QueryStat queryStat, MultipartFile file) {
        if (file == null || queryStat.getPlatformCode() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        String endpoint = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.OSS_ENDPOINT,queryStat.getPlatformCode()).getValue();
        // 在该OSS的同一地区ESC上可使用内网地址,加快传输速度
        // String endpoint = "oss-cn-qingdao-internal.aliyuncs.com";
        String accesskey = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ACCESS_KEY_ID,queryStat.getPlatformCode()).getValue();
        String secret = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ACCESS_KEY_SECRET,queryStat.getPlatformCode()).getValue();
        String bucketName = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.BUCKET_NAME,queryStat.getPlatformCode()).getValue();
        String url = AliOss.of(endpoint, accesskey, secret, bucketName).upload(file);
        Map<String, String> result = new HashMap<>();
        result.put("url",url);
        return BaseResponseParam.success(result);
    }

    @Override
    public String downloadFile(String localPath,String fileName, Integer platformCode) {
        String endpoint = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.OSS_ENDPOINT,platformCode).getValue();
        String accesskey = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ACCESS_KEY_ID,platformCode).getValue();
        String secret = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.ACCESS_KEY_SECRET,platformCode).getValue();
        String bucketName = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.BUCKET_NAME,platformCode).getValue();
        boolean b = AliOss.of(endpoint, accesskey,secret, bucketName).downloadFile(fileName, localPath);
        if(b){
            String localFileName = fileName.substring(fileName.indexOf(endpoint) + endpoint.length() + 1);
            return localPath + localFileName;
        }
        return null;
    }
}
