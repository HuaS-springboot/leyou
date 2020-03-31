package com.vsj.material.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private String phone;
    private Integer platformCode;

}
