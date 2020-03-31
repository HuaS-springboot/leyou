package com.vsj.dao;

import com.vsj.mapper.RegisterRecordMapper;
import com.vsj.model.RegisterRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname RegisterRecordDAO
 * @Description DAO
 * @Date 2019/8/26 17:00
 * @Created by wangzx
 */
@Component
public class RegisterRecordDAO {

    @Autowired
    private RegisterRecordMapper registerRecordMapper;

    public int insertRegisterRecord(RegisterRecord registerRecord) {
        return registerRecordMapper.insertRegisterRecord(registerRecord);
    }
}
