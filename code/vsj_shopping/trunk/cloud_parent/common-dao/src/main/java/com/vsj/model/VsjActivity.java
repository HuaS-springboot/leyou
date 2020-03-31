package com.vsj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname VsjActivity
 * @Description TODO
 * @Date 2019/7/29 14:48
 * @Created by zy
 */
@Data
public class VsjActivity implements Serializable {

    private static final long serialVersionUID = -6012904779986937354L;
    private Integer id;
    private Integer productId;
    private Integer activityType;
    private String  activityName;
    private String  startTime;
    private String  endTime;
    private Integer activityStatus;
    private Integer participateNumber;
    private String  productName;
    private String  createTime;
    private Integer regId;
    private String  nickName;
    private Integer platformCode;
    private String productImage;
}
