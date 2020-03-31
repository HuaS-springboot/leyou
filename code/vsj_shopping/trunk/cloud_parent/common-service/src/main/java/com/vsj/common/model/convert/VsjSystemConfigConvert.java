package com.vsj.common.model.convert;

import org.springframework.stereotype.Component;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.request.SysConfig;
import com.vsj.model.VsjSysConfig;

/**
 * 
 * @ClassName: VsjSystemConfigConvert
 * @Description: systemConfig转换类
 * @author: mario 
 * @date: 2019年7月31日 下午5:54:37
 * @copyright: 青岛微视角文化传媒有限公司
 */
@Component
public class VsjSystemConfigConvert extends AbstractObjectConverter<SysConfig,VsjSysConfig>{

	@Override
	protected void convertImpl(SysConfig source, VsjSysConfig target) {
		target.setKey(source.getKey());
		target.setValue(source.getValue());
	}

	@Override
	protected void reConvertImpl(VsjSysConfig source, SysConfig target) {
		// TODO Auto-generated method stub
		
	}



}
