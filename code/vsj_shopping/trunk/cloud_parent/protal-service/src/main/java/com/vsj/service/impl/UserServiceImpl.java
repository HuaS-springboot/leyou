package com.vsj.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.WxPayKit;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.BaseController;
import com.vsj.common.config.WeChatApi;
import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.common.utils.MapUtils;
import com.vsj.common.utils.OrderNoUtils;
import com.vsj.common.utils.TreeUtils;
import com.vsj.common.utils.UUIDGeneratorUtils;
import com.vsj.dao.*;
import com.vsj.model.*;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.MemberTeamResponse;
import com.vsj.model.wechat.Jscode2SessionResponse;
import com.vsj.model.wechat.UnifiedOrderRequest;
import com.vsj.service.MemberProfitService;
import com.vsj.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Date 2019/7/31 10:38
 * @Created by zy
 */
@Service
public class UserServiceImpl extends BaseController implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> converter;

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private WeChatApi weChatApi;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private VsjSysConfigDAO vsjSysConfigDAO;
    @Autowired
    private VsjRegisterDAO vsjRegisterDAO;
    @Autowired
    private MemberProfitService memberProfitService;

    @Autowired
    private RegisterRecordDAO registerRecordDAO;
    @Autowired
    private RegisterChargeDAO registerChargeDAO;
    /**
     * @Description 微信登录
     * @Author zy
     * @Date   2019/7/31 19:30
     * @Param  [code]
     * @Return      java.lang.Object
     * @Exception
     *
     */
    @Override
    public BaseResponseParam doLogin(QueryStat queryStat) {

        // 获取商户mchid
        String mchid = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHID, queryStat.getPlatformCode()).getValue();
        // 获取API KEY
        String apiKey = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_APISECRET, queryStat.getPlatformCode()).getValue();
        // 获取mch_appid
        String mchAppId = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHAPPID, queryStat.getPlatformCode()).getValue();
        UnifiedOrderRequest request=new UnifiedOrderRequest();
        request.setMch_id(mchid);
        request.setKey(apiKey);
        request.setAppid(mchAppId);
        request.setCode(queryStat.getCode());
        Jscode2SessionResponse json = weChatApi.getWebAccess(request);
        if(json == null){
            return BaseResponseParam.fail("参数错误");
        }else{
            if(json.getOpenid() == null || json.getSession_key()==null) {
                return BaseResponseParam.fail("参数错误");
            }else {
                VsjRegister register=vsjRegisterDAO.getUserByOpenId(json.getOpenid());
                if (register == null){
                    //登录
                    String key = RedisKeyConstant.USER_LOGIN_WECHAT_TOKEN_KEY;
                    String token = UUIDGeneratorUtils.getUUIDLower();
                    redisClient.set(key + register.getId(), token, 30L, TimeUnit.DAYS);
                    register.setToken(token);
                    register.setStatus(0);
                    BaseResponseParam.success(register);
                }else{
                    //注册
                    VsjRegister r = new VsjRegister();
                    r.setOpenId(json.getOpenid());
                    r.setPlatformCode(getPlatformCode());
                    int count=vsjRegisterDAO.insertUser(r);
                    if (count > 0){
                        String key = RedisKeyConstant.USER_LOGIN_WECHAT_TOKEN_KEY;
                        String token = UUIDGeneratorUtils.getUUIDLower();
                        redisClient.set(key + r.getId(), token, 30L, TimeUnit.DAYS);
                        register.setToken(token);
                        register.setStatus(1);
                        BaseResponseParam.success(r);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object saveUserInfo(String uid,String rawData) {

        if (StringUtils.isAnyBlank(uid,rawData)){
         return   BaseResponseParam.fail("参数错误");
        }
        JSONObject rawDataJson = JSON.parseObject( rawData );
        String nickName = rawDataJson.getString( "nickName" );
        String avatarUrl = rawDataJson.getString( "avatarUrl" );
        String gender  = rawDataJson.getString( "gender" );
        String city = rawDataJson.getString( "city" );
        String country = rawDataJson.getString( "country" );
        String province = rawDataJson.getString( "province" );
        String language = rawDataJson.getString("language");
        VsjRegister user = new VsjRegister();
        user.setId(Integer.parseInt(uid));
        user.setNickName( nickName );
        user.setProvince(province);
        user.setCountry(country);
        user.setLanguage(language);
        user.setHeadUrl(avatarUrl);
        user.setCity(city);
        user.setSex(Integer.parseInt(gender));
        int count=  vsjRegisterDAO.saveUserInfo( user );
        if(count > 0){
            BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    @Override
    public BaseResponseParam getMemberTeamProfit(QueryStat queryStat) {
        if(queryStat.getRegId() == null || queryStat.getPlatformCode() == null){
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = converter.convert(queryStat,BaseQueryStat.class);
        List<MemberTeamResponse> dbList = memberProfitService.getMemberProfitList(baseQueryStat);
        for (MemberTeamResponse memberTeamResponse : dbList) {
            if(memberTeamResponse.getRegId() == queryStat.getRegId()){
                memberTeamResponse.setParentId(0);
            }
        }
        String jsonStr = TreeUtils.listToTree(JSON.toJSONString(dbList), "regId", "parentId", "children");
        return BaseResponseParam.success(JSON.parseArray(jsonStr,MemberTeamResponse.class));
    }

    @Override
    public BaseResponseParam chargeMoney(QueryStat queryStat) {
        //生成订单号
        OrderNoUtils a=new OrderNoUtils(0,0);
        String trade_no=a.nextId()+"";
        // 获取商户mchid
        String mchid = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHID, queryStat.getPlatformCode()).getValue();
        // 获取API KEY
        String apiKey = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_APISECRET, queryStat.getPlatformCode()).getValue();
        // 获取mch_appid
        String mchAppId = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHAPPID, queryStat.getPlatformCode()).getValue();
        BigDecimal multiple = new BigDecimal(100);
        UnifiedOrderRequest request=new UnifiedOrderRequest();
        request.setSpbill_create_ip(queryStat.getIp());
        request.setOut_trade_no(trade_no);
        request.setBody("余额充值");
        request.setTotal_fee((queryStat.getMoney().multiply(multiple))+"");
        request.setMch_id(mchid);
        request.setKey(apiKey);
        request.setAppid(mchAppId);
        Map<String,String> map = weChatApi.unifiedorder(request);
        // 判断返回的结果
        String returnCode = map.get("return_code");
        String returnMsg = map.get("return_msg");
        if (!WxPayKit.codeIsOk(returnCode)) {
            return BaseResponseParam.fail(returnMsg);
        }
        String resultCode = map.get("result_code");
        if (!WxPayKit.codeIsOk(resultCode)) {
            BaseResponseParam.fail(returnMsg);
        }
        //数据库生成预订单
        VsjRegisterCharge registerCharge=new VsjRegisterCharge();
        registerCharge.setChargeNo(trade_no);
        registerCharge.setChargeMoney(queryStat.getMoney());
        registerCharge.setPayChannel(queryStat.getPayType());
        registerCharge.setPlatformCode(queryStat.getPlatformCode());
        registerCharge.setRegId(queryStat.getRegId());
        int i=registerChargeDAO.addRegisterCharge(registerCharge);
        if (i>0){
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String prepayId = map.get("prepay_id");
        // 二次签名，构建公众号唤起支付的参数,这里的签名方式要与上面统一下单请求签名方式保持一致
        Map<String, String> packageParams = WxPayKit.prepayIdCreateSign(prepayId,
                mchAppId,apiKey, SignType.HMACSHA256);
        // 将二次签名构建的数据返回给前端并唤起公众号支付
        String jsonStr = JSON.toJSONString(packageParams);
        return BaseResponseParam.success(jsonStr);
        }
        return BaseResponseParam.fail();
    }

    @Override
    public void chargeNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        System.out.println("接收到的报文：" + notityXml);
        //转map
        Map map = MapUtils.xmlToMap(notityXml);
        String resXml = "";
        logger.info("【小程序支付回调】 回调数据： \n"+map);
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equalsIgnoreCase(returnCode)) {
            String returnmsg = (String) map.get("result_code");
            if("SUCCESS".equals(returnmsg)){
                //TODO 处理业务
                int i=registerChargeDAO.updateChargeStatus(map.get("out_trade_no").toString(),map.get("transaction_id").toString());
                if(i > 0){
                    //查询订单金额和用户ID
                    VsjRegisterCharge register=registerChargeDAO.getRegisterByOpenId(map.get("out_trade_no").toString());
                    if (register == null){
                        resXml = "FAIL";
                        logger.info("系统异常:"+resXml);
                    }
                    int j=vsjRegisterDAO.updateRegisterMoneyAdd(register);
                    if (j>0){
                        //查询支付完之后的余额
                        VsjRegister reg=vsjRegisterDAO.getUserById(register.getRegId(),register.getPlatformCode());
                        //插入支出记录
                        RegisterRecord record=new RegisterRecord();
                        record.setCarryBalance(register.getChargeMoney());
                        record.setPlatformCode(register.getPlatformCode());
                        record.setRegId(register.getRegId());
                        record.setSource("充值余额");
                        record.setType(0);
                        record.setTotalBalance(reg.getCarryBalance());
                        int k=registerRecordDAO.insertRegisterRecord(record);
                        if (k>0) {
                            resXml = "SUCCESS";
                        }
                    }
                }
            }else{
                resXml = "FAIL";
                logger.info("支付失败:"+resXml);
            }
        }else{
            resXml = "FAIL";
            logger.info("【订单支付失败】");
        }
        logger.info("【小程序支付回调响应】 响应内容：\n"+resXml);
        response.getWriter().print(resXml);
    }
}
