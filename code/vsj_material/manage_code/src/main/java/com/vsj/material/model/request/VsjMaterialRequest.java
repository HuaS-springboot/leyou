package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname VsjMaterialRequest
 * @Description 素材管理参数接受
 * @Date 2019/8/13 13:43
 * @Created by sxm
 */
@Data
public class VsjMaterialRequest implements Serializable {
    private static final long serialVersionUID = 1111381823524976466L;
    private Integer id;
    private String title;
    private String image;
    private String content;
    private Integer sort;
    private Integer collectionNum;
    private Integer downloadNum;
    private Integer sysUsrId;
    private Integer status;
    private Integer oneCateId;
    private Integer twoCateId;
    private Integer threeCateId;
    private String  nickName;
    private Integer resourceType;
}
