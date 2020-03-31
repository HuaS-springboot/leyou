package com.vsj.material.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.model.request.QueryStat;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname UploadService
 * @Description OSS上传文件接口
 * @Date 2019/7/30 11:42
 * @Created by sxm
 */
public interface UploadService {

    BaseResponseParam uploadFile(QueryStat queryStat, MultipartFile file);

    String downloadFile(String localPath, String fileName, Integer platformCode);
}
