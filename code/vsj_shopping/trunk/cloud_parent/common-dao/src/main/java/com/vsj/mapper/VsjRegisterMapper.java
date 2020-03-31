package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjOrder;
import com.vsj.model.VsjRegister;
import com.vsj.model.VsjRegisterCharge;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface VsjRegisterMapper {

    @UpdateProvider(type = VsjRegisterMapper.Provider.class, method = "updateRegisterDetail")
    int updateRegisterDetail(VsjRegister vsjRegister);

    @Select("SELECT * FROM vsj_register WHERE open_id = #{openid}")
    VsjRegister getUserByOpenId(String openid);

    @Insert("INSERT INTO vsj_register (open_id,create_time) VALUES(#{openId},#{createTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertUser(VsjRegister r);

    @UpdateProvider(type = VsjRegisterMapper.Provider.class,method = "updateUserInfo")
    int saveUserInfo(VsjRegister user);

    @Update("UPDATE FROM vsj_register SET carry_balance = carry_balance - #{orderAmountTotal} WHERE id = #{regId} AND carry_balance>#{orderAmountTotal}")
    int updateRegisterMoney(VsjOrder o);

    @Select("SELECT * FROM vsj_register WHERE id = #{id} and platform_code = #{platformCode}")
    VsjRegister getUserById(@Param("id") Integer id, @Param("platformCode") Integer platformCode);

    @Update("UPDATE FROM vsj_register carry_balance = carry_balance+#{chargeMoney} WHERE id = #{regId}")
    int updateRegisterMoneyAdd(VsjRegisterCharge register);

    class Provider{
        public String updateRegisterDetail(VsjRegister vsjRegister){
            String sql = new SQL(){{
                UPDATE("vsj_register");
                if(StringUtil.isNoEmptyStr(vsjRegister.getNickName())){
                    SET("nick_name = #{nickName}" );
                }
                if(StringUtil.isNoEmptyStr(vsjRegister.getTelPhone())){
                    SET("tel_phone = #{telPhone}" );
                }
                if(vsjRegister.getCarryBalance() != null){
                    SET("carry_balance = carry_balance + #{carryBalance}" );
                }
                if(StringUtil.isNoEmptyStr(vsjRegister.getRemark())){
                    SET("remark = #{remark}" );
                }
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
        public String updateUserInfo(VsjRegister user){
            String sql = new SQL(){{
                UPDATE("vsj_register");
                if(StringUtil.isNoEmptyStr(user.getNickName())){
                    SET("nick_name = #{nickName}");
                }
                if(StringUtil.isNoEmptyStr(user.getHeadUrl())){
                    SET("head_url = #{headUrl}");
                }
                if(StringUtil.isNoEmptyStr(user.getProvince())){
                    SET("province = #{province}");
                }
                if(StringUtil.isNoEmptyStr(user.getCountry())){
                    SET("country = #{country}");
                }
                if(StringUtil.isNoEmptyStr(user.getCity())){
                    SET("city = #{city}");
                }
                if(StringUtil.isNoEmptyStr(user.getLanguage())){
                    SET("language = #{language}");
                }
                if(user.getSex() != null){
                    SET("sex = #{sex}");
                }
                WHERE("id = #{id}");
            }}.toString();
            return sql;
        }
    }
}
