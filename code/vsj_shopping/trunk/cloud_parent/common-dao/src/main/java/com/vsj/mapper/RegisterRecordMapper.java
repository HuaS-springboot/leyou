package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.RegisterRecord;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Classname RegisterRecordMapper
 * @Description Mapper
 * @Date 2019/8/26 17:01
 * @Created by wangzx
 */
@Mapper
public interface RegisterRecordMapper {

    @InsertProvider(type = RegisterRecordMapper.RegisterRecordProvider.class,method = "insertRegisterRecord")
    int insertRegisterRecord(RegisterRecord registerRecord);

    class RegisterRecordProvider{

        public String insertRegisterRecord(RegisterRecord registerRecord){
            String sql = new SQL(){{
                INSERT_INTO("vsj_register_record");
                VALUES("reg_id","#{regId}");
                VALUES("carry_balance","#{carryBalance}");
                VALUES("total_balance","#{totalBalance}");
                VALUES("type","#{type}");
                VALUES("platform_code","#{platformCode}");
                VALUES("create_time","NOW()");
                if(StringUtil.isNoEmptyStr(registerRecord.getSource())){
                    VALUES("source","#{source}");
                }
            }}.toString();
            return sql;
        }
    }
}
