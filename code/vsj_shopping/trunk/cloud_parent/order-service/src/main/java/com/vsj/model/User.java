package com.vsj.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private int  id;

    private String nickname;

    private int sex;

    private String telphone;
}
