package com.vsj.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @Classname OSSUtils
 * @Description OSS对象存储工具类
 * @Date 2019/7/30 13:29
 * @Created by wangzx
 */
public class OSSUtils {

    public static boolean putObject(String endpoint, String accesskey, String secret, String bucketName, String filename, InputStream is, long length) throws Exception {
        //服务器端生成url签名字串
        OSSClient Server = new OSSClient(endpoint, accesskey, secret);
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, filename, HttpMethod.PUT);
        //设置过期时间
        request.setExpiration(expiration);
        //设置Content-Type
        request.setContentType("application/octet-stream");
        // 添加user meta
        request.addUserMetadata("author", "vsj");
        // 生成URL签名(HTTP PUT请求)
        URL signedUrl = Server.generatePresignedUrl(request);

        //客户端使用使用url签名字串发送请求
        OSSClient client = new OSSClient(endpoint, accesskey, secret);
        // 添加PutObject请求头
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("Content-Type", "application/octet-stream");
        // 添加user meta
        customHeaders.put("x-oss-meta-author", "vsj");
        PutObjectResult result = client.putObject(signedUrl, is, length, customHeaders);
        System.out.println(signedUrl.toString());
        client.shutdown();
        return result != null && StringUtils.isNotBlank(result.getETag());
    }

    public static void delObjects(String endpoint, String accesskey, String secret, String bucketName, List<String> keys) {
        OSSClient client = new OSSClient(endpoint, accesskey, secret);
        DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
//        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        // 关闭client
        client.shutdown();
    }

    /**
     * OSS下载文件
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     * @param bucketName
     * @param objectName
     * @param filePath
     */
    public static void getObject(String endpoint,String accessKeyId,String accessKeySecret,String bucketName,String objectName,
                                   String filePath){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(filePath));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
