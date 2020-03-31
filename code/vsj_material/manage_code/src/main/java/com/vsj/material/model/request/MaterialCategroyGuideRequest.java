package com.vsj.material.model.request;

import lombok.Data;

import java.io.Serializable;
/**
 * @Author: sxm
 * @Date :2019/8/16 19:35
 * @Describe: 分类导航接收类
 */
@Data
public class MaterialCategroyGuideRequest implements Serializable {

    private static final long serialVersionUID = 1530540223760990496L;
    private Integer id;
    private String imageUrl;
    private Integer categoryId;

}
