package com.vsj.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import com.vsj.model.KafkaSendModel;

@Mapper
public interface KafkaSendMapper {
	
	public static final String tableName = "vsj_kafka_send";
	/**
	 * 
	 * @Title: insert
	 * @Description: 新增数据
	 * @param kafkaSend
	 * @return
	 * @author mario
	 * @return: String
	 */
	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
	@InsertProvider(type = KafkaSendMapper.KafkaSendProvider.class, method = "insert")
	int insert(KafkaSendModel kafkaSend);
	
	/**
	 * 
	 * @Title: deleteByPrimaryKey
	 * @Description: 根据主键删除
	 * @param id
	 * @return
	 * @author mario
	 * @return: String
	 */
	@DeleteProvider(type = KafkaSendMapper.KafkaSendProvider.class, method = "deleteByPrimaryKey")
	int deleteByPrimaryKey(Integer id);
	
	/**
	 * 
	 * @Title: updateFileCount
	 * @Description: 更新失败次数
	 * @param id
	 * @return
	 * @author mario
	 * @return: int
	 */
	@UpdateProvider(type = KafkaSendMapper.KafkaSendProvider.class, method = "updateFileCount")
	int updateFileCount(Integer id);

	class KafkaSendProvider {
		public String insert(KafkaSendModel kafkaSend) {
			String sql = new SQL() {
				{
					INSERT_INTO(tableName);
					// 必填字段
					VALUES("id", "#{id}");
					VALUES("topic", "#{topic}");
					VALUES("record", "#{record}");
					// 非必填
					if (null != kafkaSend.getStatus()) {
						VALUES("status", "#{status}");
					}
					if (null != kafkaSend.getNum()) {
						VALUES("num", "#{num}");
					}
					if (null != kafkaSend.getCreateTime()) {
						VALUES("create_time", "#{createTime}");
					}
					if (null != kafkaSend.getUpdateTime()) {
						VALUES("update_time", "#{updateTime}");
					}
					if (null != kafkaSend.getItemId()) {
						VALUES("item_id", "#{itemId}");
					}
					if(null != kafkaSend.getKey()){
						VALUES("key", "#{key}");
					}
				}
			}.toString();
			return sql;
		}
		
		public String deleteByPrimaryKey(Integer id) {
			String sql = new SQL() {
				{
					DELETE_FROM(tableName);
					WHERE("id = #{id}");

				}
			}.toString();
			return sql;
		}
		
		public String updateFileCount(Integer id){
			String sql = new SQL(){
				{
					UPDATE(tableName);
					SET("update_time = now(), status = 1 ,num = num +1");
					WHERE("id = #{id}");
				}
			}.toString();
			return sql;
		}
	}
}
