package com.vsj.dao;

import com.vsj.mapper.VsjRegisterMapper;
import com.vsj.model.VsjOrder;
import com.vsj.model.VsjRegister;
import com.vsj.model.VsjRegisterCharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VsjRegisterDAO {
    @Autowired
    private VsjRegisterMapper vsjRegisterMapper;

    public int updateRegisterDetail(VsjRegister vsjRegister){
        return vsjRegisterMapper.updateRegisterDetail(vsjRegister);
    }

    public VsjRegister getUserByOpenId(String openid) {

        return vsjRegisterMapper.getUserByOpenId(openid);
    }

    public int insertUser(VsjRegister r) {
        return vsjRegisterMapper.insertUser(r);
    }

    public int saveUserInfo(VsjRegister user) {
        return vsjRegisterMapper.saveUserInfo(user);
    }

    public int updateRegisterMoney(VsjOrder o) {
        return vsjRegisterMapper.updateRegisterMoney(o);
    }

    public VsjRegister getUserById(Integer id, Integer platformCode) {
        return vsjRegisterMapper.getUserById(id,platformCode);
    }

    public int updateRegisterMoneyAdd(VsjRegisterCharge register) {
        return vsjRegisterMapper.updateRegisterMoneyAdd(register);
    }
}
