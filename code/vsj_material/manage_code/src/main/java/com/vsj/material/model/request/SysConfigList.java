package com.vsj.material.model.request;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname SysConfigList
 * @Description 系统配置参数集合
 * @Date 2019/8/13 14:43
 * @Created by wangzx
 */

@Data
public class SysConfigList implements Serializable {

    private static final long serialVersionUID = -3495853486412800738L;

    private List<SysConfig> sysConfigList = new ArrayList<>();

}
