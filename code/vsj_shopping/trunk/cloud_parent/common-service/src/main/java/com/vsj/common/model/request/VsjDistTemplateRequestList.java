package com.vsj.common.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname VsjDistTemplateRequestList
 * @Description TODO
 * @Date 2019/8/7 9:46
 * @Created by wangzx
 */
@Data
public class VsjDistTemplateRequestList implements Serializable {

    private static final long serialVersionUID = -3466914425777348697L;

    private VsjSysDistTemplateRequest parentDist;

    private List<VsjSysDistTemplateRequest> children = new ArrayList<>();
}
