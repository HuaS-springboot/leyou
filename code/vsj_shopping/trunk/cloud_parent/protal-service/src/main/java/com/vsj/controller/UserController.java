package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.utils.HttpUtils;
import com.vsj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2019/7/30 18:12
 * @Created by zy
 */
@RestController
@RequestMapping(value="/api/user/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
    /**
     * 微信小程序的登录获取用户唯一的openID和session
     * @param queryStat 小程序前端获取用户表示
     */
    @RequestMapping("/login")
    public BaseResponseParam doLogin(@RequestBody QueryStat queryStat){
        if (null == getPlatformCode()) {
            logger.info("请求没有携带平台码platformCode");
            return BaseResponseParam.fail("请刷新重试");
        }
        logger.debug("code"+queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        long startTime = System.currentTimeMillis();
        BaseResponseParam responseParam=userService.doLogin(queryStat);
        logger.info("信息请求完成,返回参数={},耗时={}", responseParam, (System.currentTimeMillis() - startTime));
        return responseParam;
    }

    @GetMapping("saveUserInfo")
    public Object saveUserInfo(String uid,String rawData){
        return userService.saveUserInfo(rawData,uid);
    }

    /**
     * 查询会员团队奖励
     * @param queryStat
     * nickName:昵称
     * @return
     */
    @PostMapping("/getMemberTeamProfit")
    public BaseResponseParam getMemberTeamProfit(@RequestBody QueryStat queryStat){
        logger.info("查询会员团队奖励入参={}",queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        queryStat.setRegId(getRegisterId());
        BaseResponseParam responseParam = userService.getMemberTeamProfit(queryStat);
        logger.info("查询会员团队奖励出参={}",responseParam);
        return responseParam;

    }

    /**
     * @Description 用户充值
     * @Author zy
     * @Date   2019/8/26 15:50
     * @Param  [queryStat]
     * money:充值金额
     * @Return      com.vsj.common.model.BaseResponseParam
     * @Exception
     *
     */
    @RequestMapping("chargeMoney")
    public BaseResponseParam chargeMoney(HttpServletRequest request, @RequestBody QueryStat queryStat){

        logger.info("用户充值入参={}",queryStat);
        queryStat.setPlatformCode(getPlatformCode());
        queryStat.setRegId(getRegisterId());
        String ip= HttpUtils.getIp(request);
        queryStat.setIp(ip);
        BaseResponseParam responseParam=userService.chargeMoney(queryStat);
        logger.info("用户充值出参={}",responseParam);
        return responseParam;
    }

    @RequestMapping("chargeNotify")
    public void chargeNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        userService.chargeNotify(request,response);
    }
}
