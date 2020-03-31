package com.vsj.material.model.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname QueryStat
 * @Description 公共查询类
 * @Date 2019/8/13 10:29
 * @Created by wangzx
 */
@Data
public class QueryStat implements Serializable {

    private Integer id;
    private Integer type;
    private String content;
    private Integer platformCode;
    @Builder.Default
    private Integer pageNum = 1;
    @Builder.Default
    private Integer pageSize = 10;
    private String ids;
    private String nickName;
    private String telPhone;
    private Integer levelId;
    private String pswd;
    private String orderNo;
    private String phone;
    private String code;
    private Integer status;
    private Integer parentId;
    private Integer useId;
    private Integer isMylike;
    private String userName;
    private Integer oneCateId;
    private Integer twoCateId;
    private Integer threeCateId;
    private Integer resourceType;
    private String title;
}
