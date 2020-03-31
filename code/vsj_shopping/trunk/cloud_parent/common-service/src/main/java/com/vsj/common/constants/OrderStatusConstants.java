package com.vsj.common.constants;

/**
 * @Classname OrderStatusConstants
 * @Description 微视角订单常量
 * @Date 2019/8/19 15:44
 * @Created by wangzx
 */
public interface OrderStatusConstants {
    /**
     * 订单状态-待付款
     */
    public static final Integer ORDER_STATUS_PENDING_PAYMENT = 0;

    /**
     * 订单状态-待发货
     */
    public static final Integer ORDER_STATUS_PENDING_DELIVERED = 1;

    /**
     * 订单状态-待收货
     */
    public static final Integer ORDER_STATUS_PENDING_RECEIPT = 2;

    /**
     * 订单状态-已签收
     */
    public static final Integer ORDER_STATUS_RECEIVED = 3;

    /**
     * 订单状态-已结算
     */
    public static final Integer ORDER_STATUS_SETTLED = 4;

    /**
     * 订单状态-未发货退款
     */
    public static final Integer ORDER_STATUS_NO_SHIPPING_REFUND = 5;

    /**
     * 订单状态-已发货退款
     */
    public static final Integer ORDER_STATUS_SHIPPING_REFUND = 6;

    /**
     * 订单状态-已退款
     */
    public static final Integer ORDER_STATUS_REFUNDED = 7;

    /**
     * 订单状态-已关闭
     */
    public static final Integer ORDER_STATUS_CLOSED = 8;

    /**
     * 订单状态-取消发货
     */
    public static final Integer ORDER_STATUS_CANCEL_DELIVERY = 9;

    /**
     * 订单操作记录状态-未发货退款审核未通过
     */
    public static final Integer ORDER_OPER_STATUS_NO_SHIPPING_REFUND_NO_PASS = 9;

    /**
     * 订单操作记录状态-已发货退款审核未通过
     */
    public static final Integer ORDER_OPER_STATUS_SHIPPING_REFUND_NO_PASS = 10;

}
