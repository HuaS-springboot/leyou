package com.vsj.service.impl;

import com.vsj.dao.RegisterRecordDAO;
import com.vsj.model.RegisterRecord;
import com.vsj.service.RegisterRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname RegisterRecordServiceImpl
 * @Description 实现
 * @Date 2019/8/26 16:59
 * @Created by wangzx
 */
@Service
public class RegisterRecordServiceImpl implements RegisterRecordService {

    @Autowired
    private RegisterRecordDAO registerRecordDAO;

    @Override
    public int insertRegisterRecord(RegisterRecord registerRecord) {
        return registerRecordDAO.insertRegisterRecord(registerRecord);
    }
}
