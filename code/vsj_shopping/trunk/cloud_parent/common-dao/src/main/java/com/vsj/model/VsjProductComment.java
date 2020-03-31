package com.vsj.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VsjProductComment implements Serializable {
    private static final long serialVersionUID = 6027264307455166497L;
    private Integer commentId;

    private Integer productId;

    private Integer orderId;

    private Integer customerId;

    private String title;

    private String content;

    private Byte auditStatus;

    private Date auditTime;

    private Date modifiedTime;

    private Integer platformCode;
}