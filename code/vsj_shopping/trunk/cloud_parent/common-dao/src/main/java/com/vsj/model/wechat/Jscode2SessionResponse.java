package com.vsj.model.wechat;

import lombok.Data;

@Data
public class Jscode2SessionResponse {
    private String session_key;
    private String openid;
    private String unionid;
}
