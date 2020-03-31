package com.vsj.material.model.convert;

import com.vsj.material.model.VsjMaterialSysConfig;
import com.vsj.material.model.request.SysConfig;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjMaterialConvert
 * @Description 系统配置转换类
 * @Date 2019/8/13 15:08
 * @Created by wangzx
 */
@Component
public class VsjMaterialSysConfigConvert extends AbstractObjectConverter<SysConfig, VsjMaterialSysConfig> {
    @Override
    protected void convertImpl(SysConfig source, VsjMaterialSysConfig target) {
        target.setId(source.getId());
        target.setKey(source.getKey());
        target.setValue(source.getValue());
        target.setRemark(source.getRemark());
    }

    @Override
    protected void reConvertImpl(VsjMaterialSysConfig source, SysConfig target) {

    }
}
