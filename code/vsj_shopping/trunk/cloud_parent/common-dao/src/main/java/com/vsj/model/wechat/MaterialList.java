package com.vsj.model.wechat;

import com.vsj.model.VsjMaterial;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/8/9 10:53
 * @Describe:
 */
@Data
public class MaterialList {
    private List<VsjMaterial> materialList = new ArrayList<>();
}
