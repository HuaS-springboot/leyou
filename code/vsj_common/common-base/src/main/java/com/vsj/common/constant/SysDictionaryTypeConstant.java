package com.vsj.common.constant;

/**
 * 
 * @ClassName: SysDictionaryTypeConstant
 * @Description: 数据字典类型常量说明
 * @author: mario 
 * @date: 2019年8月5日 下午4:37:16
 * @copyright: 青岛微视角文化传媒有限公司
 */
public interface SysDictionaryTypeConstant {
	
	/**
	 * 支付方式
	 */
	public static final String PAY_TYPE = "pay_type";
	
	/**
	 * 订单状态
	 */
	public static final String ORDER_STATUS = "order_status";
	
	/**
	 * 快递公司
	 */
	public static final String EXPRESS_COMPANY = "express_company";

	/**
	 * 快递查询方式
	 */
	public static final String EXPRESS_API = "express_api";

	/**
	 *	文件存储方式
	 */
	public static final String FILE_STORAGE = "file_storage";

	/**
	 *	分类状态
	 */
	public static final String TYPE_STATUS="type_status";

	/**
	 * 商品状态
	 */
	public static final String GOODS_STATUS="goods_status";

	/**
	 * 活动状态
	 */
	public static final String ACTIVITY_STATUS = "activity_status";
	
	/**
	 * 字典集合
	 */
	public  static final String[] DICTIONARY = {PAY_TYPE,ORDER_STATUS,EXPRESS_COMPANY,EXPRESS_API,FILE_STORAGE,TYPE_STATUS,GOODS_STATUS,ACTIVITY_STATUS};
 
}
