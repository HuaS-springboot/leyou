package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjProductImgRequest
 * @Description 轮播/首页推荐参数接受类
 * @Date 2019/8/2 15:01
 * @Created by wangzx
 */
@Data
public class VsjProductImgRequest implements Serializable {

    private static final long serialVersionUID = 5633086561266900141L;

    private Integer id;
    private Integer productId;
    private String picDesc;
    private String picUrl;
    private Integer isMaster;
    private Integer picOrder;
    private Integer picStatus;
}
