package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjStageSchemaRequestList
 * @Description 分润管理集合
 * @Date 2019/8/14 13:29
 * @Created by wangzx
 */
@Data
public class VsjStageSchemaRequestList implements Serializable {

    private Integer productId;

    private Integer isDealer;

    private Integer isCommission;

    private List<VsjStageSchemaRequest> schemaRequestList = new ArrayList<>();
}
