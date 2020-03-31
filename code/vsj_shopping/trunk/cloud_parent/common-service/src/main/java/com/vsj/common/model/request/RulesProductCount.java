package com.vsj.common.model.request;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RulesProductCount
 * @Description TODO
 * @Date 2019/8/5 15:01
 * @Created by wangzx
 */
@Data
public class RulesProductCount implements Serializable {

    private static final long serialVersionUID = 8104656419404685908L;

    /**
     * 购买商品的id
     */
    private String productId;

    /**
     * 购买该商品的次数
     */
    private Integer count;

    /**
     * 是否开启 0=不启用；1=启用
     */
    private Integer status;
}
