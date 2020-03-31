package com.vsj.dao;

import com.vsj.mapper.RegisterChargeMapper;
import com.vsj.model.VsjRegister;
import com.vsj.model.VsjRegisterCharge;
import com.vsj.model.wechat.UnifiedOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname RegisterChargeDAO
 * @Description TODO
 * @Date 2019/8/26 17:17
 * @Created by zy
 */
@Component
public class RegisterChargeDAO {
    @Autowired
    private RegisterChargeMapper registerChargeMapper;
    public int addRegisterCharge(VsjRegisterCharge registerCharge) {
        return registerChargeMapper.addRegisterCharge(registerCharge);
    }

    public int updateChargeStatus(String out_trade_no, String transaction_id) {
        return registerChargeMapper.updateChargeStatus(out_trade_no,transaction_id);
    }

    public VsjRegisterCharge getRegisterByOpenId(String out_trade_no) {
        return registerChargeMapper.getRegisterByOpenId(out_trade_no);
    }
}
