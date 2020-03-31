package com.vsj.service;

import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2019/7/31 10:38
 * @Created by zy
 */
public interface UserService {
    /**
    * @Description 微信登录
    * @Author zy
    * @Date   2019/7/31 17:03
    * @Param
    * @Return
    * @Exception
    *
    */
    BaseResponseParam doLogin(QueryStat queryStat);

    /**
    * @Description 保存用户信息
    * @Author zy
    * @Date   2019/7/31 18:38
    * @Param
    * @Return
    * @Exception
    *
    */
    Object saveUserInfo(String uid,String rawData);

    BaseResponseParam getMemberTeamProfit(QueryStat queryStat);

    BaseResponseParam chargeMoney(QueryStat queryStat);

    void chargeNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
