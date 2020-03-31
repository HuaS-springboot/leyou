package com.vsj.dao;

import com.vsj.mapper.SaasSystemMapper;
import com.vsj.model.VsjSaasSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname SaasSystemDAO
 * @Description TODO
 * @Date 2019/7/31 15:25
 * @Created by wangzx
 */
@Component
public class SaasSystemDAO {

    @Autowired
    private SaasSystemMapper saasSystemMapper;

    public int insertSaasSystem(VsjSaasSystem vsjSaasSystem) {
        return saasSystemMapper.insertSaasSystem(vsjSaasSystem);
    }
}
