package com.vsj.service.impl;

import com.vsj.dao.VsjRegisterDAO;
import com.vsj.model.VsjRegister;
import com.vsj.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname RegisterServiceImpl
 * @Description 注册用户相关实现
 * @Date 2019/8/27 13:53
 * @Created by wangzx
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private VsjRegisterDAO vsjRegisterDAO;

    @Override
    public int updateRegisterById(VsjRegister vsjRegister) {
        return vsjRegisterDAO.updateRegisterDetail(vsjRegister);
    }
}
