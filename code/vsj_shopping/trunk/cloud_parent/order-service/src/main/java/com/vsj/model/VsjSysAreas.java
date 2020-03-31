package com.vsj.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VsjSysAreas {
    private Integer id;
    private String code;
    private String parentCode;
    private String name;
    private String province;
    private String city;
    private String district;
    private String fullName;
    private String grade;
    private List<VsjSysAreas> children = new ArrayList<>();
}
