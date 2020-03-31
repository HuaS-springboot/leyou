package com.vsj.common.model.convert;

import com.vsj.common.AbstractObjectConverter;
import com.vsj.common.model.Order;
import com.vsj.common.utils.OrderNoUtils;
import com.vsj.model.VsjOrder;
import org.springframework.stereotype.Component;

/**
 * @Classname VsjOrderConvet
 * @Description TODO
 * @Date 2019/8/2 14:51
 * @Created by zy
 */
@Component
public class VsjOrderConvet extends AbstractObjectConverter<Order, VsjOrder> {

    @Override
    protected void convertImpl(Order order, VsjOrder vsjOrder) {
        OrderNoUtils a=new OrderNoUtils(0,0);
        vsjOrder.setOrderNo(a.nextId()+"");
        vsjOrder.setAddressId(order.getAddressId());
        vsjOrder.setOrderAmountTotal(order.getOrderAmountTotal());
        vsjOrder.setProductAmountTotal(order.getProductAmountTotal());
        vsjOrder.setPlatformCode(order.getPlatformCode());
        vsjOrder.setRegId(order.getRegId());
        vsjOrder.setOrderType(order.getOrderType());
        vsjOrder.setProductCount(order.getProductCount());
        vsjOrder.setProductName(order.getProductName());
        vsjOrder.setLogisticsFee(order.getLogisticsFee());
        vsjOrder.setDividendOrder(order.getDividendOrder());
        vsjOrder.setProductId(order.getProductId());
    }

    @Override
    protected void reConvertImpl(VsjOrder source, Order target) {

    }
}
