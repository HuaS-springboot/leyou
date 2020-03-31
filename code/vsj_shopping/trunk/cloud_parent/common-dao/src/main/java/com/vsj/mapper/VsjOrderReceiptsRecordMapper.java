package com.vsj.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import com.vsj.model.VsjOrderReceiptsRecord;

@Mapper
public interface VsjOrderReceiptsRecordMapper {
	public static final String tableName = "vsj_order_receipts_record";
	public static final String baseColumn = "`id`,`order_id`,`member_id`,`operation_money`,`create_time`,`type`";

	@InsertProvider(type = Provider.class, method = "insert")
	int insert(VsjOrderReceiptsRecord vsjOrderReceiptsRecord);

	class Provider {
		public String insert(VsjOrderReceiptsRecord vsjOrderReceiptsRecord) {
			String sql = new SQL() {
				{
					INSERT_INTO(tableName);
					VALUES("order_id", "#{orderId}");
					VALUES("member_id", "#{memberId}");
					VALUES("operation_money", "#{operationMoney}");
					VALUES("type", "#{type}");
					VALUES("income_source", "#{incomeSource}");
					VALUES("platform_code", "#{platformCode}");
					VALUES("level_id", "#{levelId}");
					if(vsjOrderReceiptsRecord.getIsSettle() != null){
						VALUES("is_settle","#{isSettle}");
					}
					VALUES("create_time","NOW()");
				}
			}.toString();
			return sql;
		}
	}
}
