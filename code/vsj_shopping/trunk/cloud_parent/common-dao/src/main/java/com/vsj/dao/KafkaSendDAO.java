package com.vsj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vsj.mapper.KafkaSendMapper;
import com.vsj.model.KafkaSendModel;

@Component
public class KafkaSendDAO {
	@Autowired
	private KafkaSendMapper kafkaSendMapper;
	
	/**
	 * 
	 * @Title: insert
	 * @Description: 新增数据
	 * @param kafkaSend
	 * @return
	 * @author mario
	 * @return: String
	 */
	public int insert(KafkaSendModel kafkaSend){
		return kafkaSendMapper.insert(kafkaSend);
	}
	
	/**
	 * 
	 * @Title: deleteByPrimaryKey
	 * @Description: 根据主键删除
	 * @param id
	 * @return
	 * @author mario
	 * @return: String
	 */
	public int deleteByPrimaryKey(Integer id){
		return kafkaSendMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 
	 * @Title: updateFileCount
	 * @Description: 更新失败次数
	 * @param id
	 * @return
	 * @author mario
	 * @return: int
	 */
	public int updateFileCount(Integer id){
		return kafkaSendMapper.updateFileCount(id);
	}
}
