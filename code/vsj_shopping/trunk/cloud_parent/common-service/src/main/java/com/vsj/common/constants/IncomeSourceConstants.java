package com.vsj.common.constants;

/**
 * 
 * @ClassName: IncomeSourceConstants
 * @Description: 收入来源常量类
 * @author: mario 
 * @date: 2019年7月27日 上午10:43:51
 * @copyright: 青岛微视角文化传媒有限公司
 */
public interface IncomeSourceConstants {
	
	/**
	 * 分销返利
	 */
	public static final Byte DISTRIBUTION = 1;
	
	/**
	 * 经销商极差返利
	 */
	public static final Byte RANGE = 2;
	/**
	 * 订单退款
	 */
	public static final Byte ORDER_REFUND = 3;
	/**
	 * 订单返利退款
	 */
	public static final Byte ORDER_REBATE_REFUND = 4;
	/**
	 * 余额充值
	 */
	public static final Byte BALANCE_RECHARGE = 5;
	/**
	 * 余额支付
	 */
	public static final Byte BALANCE_PAY = 6;
	/**
	 * 红包
	 */
	public static final Byte RED_ENVELOPES = 7;
	

}
