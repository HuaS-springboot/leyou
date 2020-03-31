package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.service.UploadService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname UploadController
 * @Description OSS 上传接口
 * @Date 2019/7/30 11:40
 * @Created by wangzx
 */
@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/upload/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UploadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "上传文件", notes = "上传文件", httpMethod = "POST")
    @PostMapping("uploadFile")
    public BaseResponseParam uploadFile(QueryStat queryStat, MultipartFile file){
        queryStat.setPlatformCode(getPlatformCode());
        logger.info("上传文件入参："+file);
        long startTime = System.currentTimeMillis();
        BaseResponseParam response = uploadService.uploadFile(queryStat,file);
        logger.info("上传文件出参={},耗时={}",response,(System.currentTimeMillis() - startTime));
        return response;
    }

    /**
     * 测试OSS下载文件
     * @param fileName OSS上的文件名称
     * @param localPath 下载的本地路径
     * @return
     */
    @ApiOperation(value = "下载文件", notes = "下载文件", httpMethod = "POST")
    @PostMapping("/downloadFile")
    public BaseResponseParam downloadFile(String localPath,String fileName){
        String localFilePath = uploadService.downloadFile(localPath,fileName,getPlatformCode());
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("url",localFilePath);
        return BaseResponseParam.success(resultMap);
    }
}
