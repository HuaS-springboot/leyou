package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjRegister;
import com.vsj.model.VsjRegisterCharge;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Classname RegisterChargeMapper
 * @Description TODO
 * @Date 2019/8/26 17:20
 * @Created by zy
 */
@Mapper
public interface RegisterChargeMapper {

    @InsertProvider(type =RegisterChargeMapper.RegisterChargeProvider.class,method = "addRegisterCharge")
    int addRegisterCharge(VsjRegisterCharge registerCharge);

    @Update("UPDATE FROM vsj_register_charge SET status=1,out_trade_no = #{transaction_id},pay_time =NOW() WHERE charge_no = #{out_trade_no}")
    int updateChargeStatus(String out_trade_no, String transaction_id);

    @Select("SELECT * FROM vsj_register_charge WHERE charge_no = #{out_trade_no}")
    VsjRegisterCharge getRegisterByOpenId(String out_trade_no);


    class RegisterChargeProvider{

        public String addRegisterCharge(VsjRegisterCharge registerCharge) {
            String sql = new SQL() {{
                INSERT_INTO("vsj_register_charge");
                if (registerCharge.getChargeMoney() != null) {
                    VALUES("charge_money", "#{chargeMoney}");
                }
                if (StringUtil.isNoEmptyStr(registerCharge.getChargeNo())) {
                    VALUES("charge_no", "#{chargeNo}");
                }
                if (StringUtil.isNoEmptyStr(registerCharge.getPayChannel())) {
                    VALUES("pay_channel", "#{payChannel}");
                }
                if (registerCharge.getRegId() != null) {
                    VALUES("reg_id", "#{regId}");
                }
                VALUES("create_time", "NOW()");
                VALUES("platform_code", "#{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
