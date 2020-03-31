package com.vsj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vsj.mapper.VsjOrderReceiptsRecordMapper;
import com.vsj.model.VsjOrderReceiptsRecord;

@Component
public class VsjOrderReceiptsRecordDAO {
	@Autowired
	private VsjOrderReceiptsRecordMapper vsjOrderReceiptsRecordMapper;
	
	public int insert(VsjOrderReceiptsRecord vsjOrderReceiptsRecord){
		return vsjOrderReceiptsRecordMapper.insert(vsjOrderReceiptsRecord);
	}
}
