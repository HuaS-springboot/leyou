package com.vsj.common.helper;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.base.Objects;
import com.vsj.common.model.AliVerificationResParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SendMessageHelper {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     *
     * @Title: sendMessage
     * @Description: 发送短信
     * @param telPhoneNum 手机号码
     * @param templateCode 短信模板
     * @param signName 短信签名
     * @param jsonParam 使用VerificationCodeModel.json
     * @param accessKeyId accessKeyId
     * @param secret accessKeysecret
     * @return
     * @author mario
     * @return: boolean
     */
    public boolean send(String telPhoneNum, String templateCode , String signName, String jsonParam, String accessKeyId, String secret){
        AliVerificationResParam result = sendMessage(telPhoneNum, templateCode, signName, jsonParam, accessKeyId, secret);
        if(null != result && Objects.equal(result.getCode(), "OK")){
            return true;
        }
        return false;
    }

    private AliVerificationResParam sendMessage(String telPhoneNum, String templateCode , String signName, String jsonParam, String accessKeyId, String secret){
        DefaultProfile profile = DefaultProfile.getProfile("default",accessKeyId,secret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", telPhoneNum);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", jsonParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            AliVerificationResParam result = JSON.parseObject(response.getData(), AliVerificationResParam.class);
            return result;
        } catch (Exception e){
            logger.error("短信发送异常...telPhoneNum={}",telPhoneNum);
        }
        return null;
    }

}
