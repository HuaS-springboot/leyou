package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import org.springframework.web.multipart.MultipartFile;

import com.vsj.common.model.request.QueryStat;

/**
 * @Classname UploadService
 * @Description OSS上传文件接口
 * @Date 2019/7/30 11:42
 * @Created by wangzx
 */
public interface UploadService {

    BaseResponseParam uploadFile(QueryStat queryStat, MultipartFile file);

    String downloadFile(String localPath,String fileName, Integer platformCode);
}
