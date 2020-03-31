package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjProductImg
 * @Description TODO
 * @Date 2019/7/29 9:11
 * @Created by wangzx
 */
@Data
public class VsjProductImg implements Serializable {

    private static final long serialVersionUID = 1800957030186271354L;
    private Integer id;
    private Integer productId;
    private String picDesc;
    private String picUrl;
    private Integer isMaster;
    private Integer picOrder;
    private Integer picStatus;
    private String createTime;
    private Integer platformCode;

}
