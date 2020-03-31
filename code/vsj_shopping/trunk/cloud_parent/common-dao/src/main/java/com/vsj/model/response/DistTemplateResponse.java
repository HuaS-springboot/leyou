package com.vsj.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DistTemplateResponse
 * @Description TODO
 * @Date 2019/8/20 12:00
 * @Created by wangzx
 */
@Data
public class DistTemplateResponse implements Serializable {

    private ParentDist parentDist;

    private List<DistChildren> children = new ArrayList<>();
}
