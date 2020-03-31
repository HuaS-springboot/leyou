package com.vsj.controller;

import com.vsj.common.BaseController;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.common.model.request.QueryStat;
import com.vsj.common.service.IShortMessagingService;
import com.vsj.common.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName MessageController
 * @Description: 短信通知相关接口
 * @Author mario
 * @Date 2019/8/27
 * @Version V1.0
 **/
@CrossOrigin
@RequestMapping(value ="api/v1/msg/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class MessageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IShortMessagingService shortMessagingServiceImpl;

    /**
     * 获取短信验证码
     * @param queryStat
     * @return
     */
    @ApiOperation(value = "获取手机短信验证码", notes = "获取手机短信验证码", httpMethod = "POST")
    @PostMapping("code")
    public BaseResponseParam getMessageCode(@RequestBody QueryStat queryStat) {
        if(null == queryStat || StringUtil.isEmptyStr(queryStat.getTelPhone())||null == queryStat.getType()){
            logger.debug("传入的手机号码或验证码类型为空...");
            return BaseResponseParam.fail("传入的手机号码或验证码类型为空...");
        }
        logger.info("获取手机号码={}，验证码类型={}",queryStat.getTelPhone(),queryStat.getType());
        BaseResponseParam result = shortMessagingServiceImpl.sendMessage(queryStat.getTelPhone(),queryStat.getType().byteValue(),getPlatformCode());
        logger.info("获取手机短信验证码结果={}",result);
        return result;
    }
}
