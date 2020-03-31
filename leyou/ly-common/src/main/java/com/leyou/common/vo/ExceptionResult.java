package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {
    private int code;
    private String message;
    private long timeStamp;

    public ExceptionResult(ExceptionEnum e){
        this.code = e.getCode();
        this.message = e.getMessage();
        this.timeStamp = System.currentTimeMillis();
    }

}
