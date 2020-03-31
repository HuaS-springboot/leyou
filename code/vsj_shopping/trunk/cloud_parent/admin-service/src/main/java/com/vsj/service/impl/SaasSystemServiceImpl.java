package com.vsj.service.impl;

import com.vsj.common.model.BaseResponse;
import com.vsj.dao.SaasSystemDAO;
import com.vsj.model.VsjSaasSystem;
import com.vsj.service.SaasSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname SaasSystemServiceImpl
 * @Description TODO
 * @Date 2019/7/31 15:20
 * @Created by wangzx
 */
@Service
public class SaasSystemServiceImpl implements SaasSystemService {

    @Autowired
    private SaasSystemDAO saasSystemDAO;


    @Override
    public Object insertSaasSystem(VsjSaasSystem vsjSaasSystem) {
        int count = saasSystemDAO.insertSaasSystem(vsjSaasSystem);
        if(count > 0){
            return BaseResponse.success();
        }
        return BaseResponse.fail();
    }
}
