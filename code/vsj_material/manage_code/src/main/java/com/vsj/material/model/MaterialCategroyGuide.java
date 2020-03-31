package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MaterialCategroyGuide implements Serializable {

    private static final long serialVersionUID = 8789932899132958770L;
    private Integer id;
    private String imageUrl;
    private Integer categoryId;
    private Integer platformCode;

}
