package com.vsj.material.model.convert;

import com.vsj.material.model.VsjMaterial;
import com.vsj.material.model.request.VsjMaterialRequest;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjMaterialConvert
 * @Description 素材信息转换类
 * @Date 2019/8/13 18:00
 * @Created by sxm
 */
@Component
public class VsjMaterialConvert extends AbstractObjectConverter<VsjMaterialRequest, VsjMaterial> {

    @Override
    protected void convertImpl(VsjMaterialRequest source, VsjMaterial target) {
       target.setId(source.getId());
       target.setTitle(source.getTitle());
       target.setContent(source.getContent());
       target.setImage(source.getImage());
       target.setDownloadNum(source.getDownloadNum());
       target.setSort(source.getSort());
       target.setCollectionNum(source.getCollectionNum());
       target.setStatus(source.getStatus());
       target.setSysUsrId(source.getSysUsrId());
       target.setOneCateId(source.getOneCateId());
       target.setTwoCateId(source.getTwoCateId());
       target.setThreeCateId(source.getThreeCateId());
       target.setResourceType(source.getResourceType());
    }

    @Override
    protected void reConvertImpl(VsjMaterial source, VsjMaterialRequest target) {

    }
}
