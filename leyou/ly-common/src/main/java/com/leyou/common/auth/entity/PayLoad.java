package com.leyou.common.auth.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PayLoad<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
