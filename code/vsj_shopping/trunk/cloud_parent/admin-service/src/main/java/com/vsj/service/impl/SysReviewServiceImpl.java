package com.vsj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.constants.SysConfigConstants;
import com.vsj.common.constants.WeChatConstants;
import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.TransfersModel;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.utils.HttpUtils;
import com.vsj.common.utils.StringUtil;
import com.vsj.dao.SysReviewDAO;
import com.vsj.dao.VsjRegisterDAO;
import com.vsj.dao.VsjSysConfigDAO;
import com.vsj.model.RegisterRecord;
import com.vsj.model.SysReview;
import com.vsj.model.VsjRegister;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.SysReviewResponse;
import com.vsj.service.RegisterRecordService;
import com.vsj.service.SysReviewService;
import com.vsj.service.UploadService;
import io.netty.util.internal.UnstableApi;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SysReviewServiceImpl
 * @Description 审核相关实现
 * @Date 2019/8/21 15:06
 * @Created by wangzx
 */
@Service
public class SysReviewServiceImpl implements SysReviewService {

    @Autowired
    private SysReviewDAO sysReviewDAO;
    @Autowired
    private AbstractObjectConverter<QueryStat, BaseQueryStat> converter;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private VsjSysConfigDAO vsjSysConfigDAO;
    @Autowired
    private VsjRegisterDAO vsjRegisterDAO;
    @Autowired
    private RegisterRecordService registerRecordService;

    @Override
    public BaseResponseParam getReviewList(QueryStat queryStat) {
        if (queryStat.getType() == null || queryStat.getPlatformCode() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        BaseQueryStat baseQueryStat = converter.convert(queryStat, BaseQueryStat.class);
        PageHelper.startPage(queryStat.getPageNum(), queryStat.getPageSize());
        List<SysReviewResponse> list = sysReviewDAO.getReviewList(baseQueryStat);
        PageInfo<SysReviewResponse> pageInfo = new PageInfo<>(list);
        return BaseResponseParam.success(pageInfo);
    }

    @Override
    public BaseResponseParam updateReview(QueryStat queryStat, HttpServletRequest request) {
        if (queryStat.getId() == null || queryStat.getStatus() == null || queryStat.getPlatformCode() == null) {
            return BaseResponseParam.fail("参数错误");
        }
        if (queryStat.getStatus() == 2) {
            // 提现审核不通过 修改状态和完成时间
            SysReview sysReview = new SysReview();
            sysReview.setStatus(2);
            sysReview.setId(queryStat.getId());
            sysReview.setPlatformCode(queryStat.getPlatformCode());
            int count = sysReviewDAO.updateSysReview(sysReview);
            if (count > 0) {
                return BaseResponseParam.success();
            }
        } else if (queryStat.getStatus() == 1) {
            // 提现审核通过
            if (StringUtil.isEmptyStr(queryStat.getOpenId())) {
                return BaseResponseParam.fail("参数错误");
            }
            // 获取提现金额
            SysReview sysReview = sysReviewDAO.getReviewById(queryStat.getId(), queryStat.getPlatformCode());
            if (sysReview == null || sysReview.getStatus() != 0) {
                return BaseResponseParam.fail("无此审核订单或此订单已处理");
            }
            // 构建微信企业付款接口,获取返回值
            //Map<String, String> resultMap = requestWeChat(queryStat, request, sysReview);
            // 判断返回的结果
            /*String returnCode = resultMap.get("return_code");
            String returnMsg = resultMap.get("return_msg");
            if (!WxPayKit.codeIsOk(returnCode)) {
                return BaseResponseParam.fail(returnMsg);
            }
            String resultCode = resultMap.get("result_code");
            if (!WxPayKit.codeIsOk(resultCode)) {
                return BaseResponseParam.fail(returnMsg);
            }
            //  成功-->处理业务
            // 获取微信付款单号
            String paymentNo = resultMap.get("payment_no");*/
            // 更新提现状态及时间
            SysReview review = new SysReview();
            review.setStatus(1);
            review.setId(queryStat.getId());
            review.setPlatformCode(queryStat.getPlatformCode());
            //review.setPaymentNo(paymentNo);
            sysReviewDAO.updateSysReview(review);
            // 减掉会员相应的可提现金额
            VsjRegister vsjRegister = new VsjRegister();
            vsjRegister.setId(sysReview.getRegId());
            vsjRegister.setPlatformCode(queryStat.getPlatformCode());
            // 转为负数 更新sql里面写的是+
            vsjRegister.setCarryBalance(sysReview.getWithdrawAmount().negate());
            vsjRegisterDAO.updateRegisterDetail(vsjRegister);
            // 添加用户交易明细
            VsjRegister register = vsjRegisterDAO.getUserById(queryStat.getId(),queryStat.getPlatformCode());
            RegisterRecord registerRecord = new RegisterRecord();
            registerRecord.setRegId(sysReview.getRegId());
            registerRecord.setCarryBalance(sysReview.getWithdrawAmount());
            registerRecord.setPlatformCode(queryStat.getPlatformCode());
            registerRecord.setType(1);
            registerRecord.setSource("用户提现");
            registerRecord.setTotalBalance(register.getCarryBalance());
            registerRecordService.insertRegisterRecord(registerRecord);
            return BaseResponseParam.success();
        }
        return BaseResponseParam.fail();
    }

    private Map<String, String> requestWeChat(QueryStat queryStat, HttpServletRequest request, SysReview sysReview) {
        // 获取商户mchid
        String mchid = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHID, queryStat.getPlatformCode()).getValue();
        // 获取API KEY
        String apiKey = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_APISECRET, queryStat.getPlatformCode()).getValue();
        // 获取mch_appid
        String mchAppId = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_MCHAPPID, queryStat.getPlatformCode()).getValue();
        // 构建请求参数
        Map<String, String> paramMap = TransfersModel.builder()
                .mch_appid(mchAppId)
                .mchid(mchid)
                .nonce_str(WxPayKit.generateStr())
                .partner_trade_no(WxPayKit.generateStr())
                .check_name("NO_CHECK")
                .openid(queryStat.getOpenId())
                .amount(sysReview.getWithdrawAmount().multiply(new BigDecimal(100)).toString())
                .desc("提现")
                .spbill_create_ip(HttpUtils.getIp(request))
                .build()
                .creatSign(apiKey, SignType.HMACSHA256);
        // 获取证书名称
        String fileName = vsjSysConfigDAO.selectByConfigName(SysConfigConstants.WX_PAY_CERT_FILE, queryStat.getPlatformCode()).getValue();
        // 下载证书、获取证书地址
        String certPath = uploadService.downloadFile(WeChatConstants.WECHAT_CERT_DOWNLOAD_PATH, fileName, queryStat.getPlatformCode());
        // 请求微信 证书密码默认为mach_id
        String xmlResult = WxPayApi.transfers(paramMap, certPath, mchid);
        // 将请求返回的 xml 数据转为 Map，方便后面逻辑获取数据
        return WxPayKit.xmlToMap(xmlResult);
    }
}
