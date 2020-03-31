package com.vsj.model.response;

import com.vsj.model.VsjActivityGroup;
import com.vsj.model.VsjActivitySeckill;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname ActivityResponse
 * @Description TODO
 * @Date 2019/7/29 16:50
 * @Created by zy
 */
@Data
public class ActivityResponse implements Serializable {

    private Integer id;
    private Integer productId;
    private String  activityName;
    private String  startTime;
    private String  endTime;
    private String  productName;
    private Integer sysUserId;
    private String  nickName;
    private Integer platformCode;
    private Integer activityType;
    private Integer activityStatus;
    private Integer participateNumber;
    private List<VsjActivityGroup> vsjActivityGroup;
    private List<VsjActivitySeckill> vsjActivitySeckills;


}
