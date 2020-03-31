package com.vsj.common.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: VerificationCodeModel
 * @Description: 阿里云短信验证码Code对象
 * @author: mario
 * @date: 2019年7月31日 下午5:54:15
 * @copyright: 青岛微视角文化传媒有限公司
 */
@Data
public class VerificationCodeModel implements Serializable {

    private static final long serialVersionUID = 4083283459337175701L;
    /**
     * 验证码
     */
    private String code;

    public VerificationCodeModel() {

    }

    public VerificationCodeModel(String code) {
        this.code = code;
    }

    public static String json(String code) {
        return JSON.toJSONString(new VerificationCodeModel(code));
    }
}
