package com.vsj.material.service.impl;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.material.dao.UserDAO;
import com.vsj.material.model.User;
import com.vsj.material.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public BaseResponseParam ok(User user) {
        if(userDAO.insert(user)>0){
            return BaseResponseParam.success();
        }else{
            return  BaseResponseParam.fail();
        }

    }
}
