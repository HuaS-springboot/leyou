package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;
/**
 * @Classname VsjCollection
 * @Description 素材用户关联表
 * @Date 2019/8/16 11:50
 * @Created by wd
 */
@Data
public class VsjCollection implements Serializable {

    private Integer id;
    private Integer mId;
    private Integer rId;
    private Integer platformCode;
}
