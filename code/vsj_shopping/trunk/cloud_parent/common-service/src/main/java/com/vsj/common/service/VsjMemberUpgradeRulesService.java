package com.vsj.common.service;

import com.vsj.common.model.Order;

/**
 * @Classname VsjMemberUpgradeRulesService
 * @Description 会员升级业务接口
 * @Date 2019/8/5 15:26
 * @Created by wangzx
 */
public interface VsjMemberUpgradeRulesService {

    /**
     *  处理会员升级业务
     * @param order
     */
    void disposeMemberLevel(Order order);
}
