package com.vsj.common.redis.constant;  

/**
 * 
 * @ClassName: RedisKeyConstant
 * @Description: 缓存key常量类
 * @author: mario 
 * @date: 2019年7月22日 下午4:33:06
 * @copyright: 青岛微视角文化传媒有限公司
 */
public interface RedisKeyConstant {
	/**
	 * 测试KEY
	 * 
	 */
	public static final String TEST = "vsj_common:user_register_code:";

	/**
	 * 系统省市区key
	 */
	public static final String SYSTEM_AREAS_KEY = "vsj_common:sys_areas:";

	/**
	 * 系统配置表key
	 */
	public static final String SYSTEM_CONFIG_KEY = "vsj_common:sys_%s_config:";

	/**
	 * 商品一二三级分类key
	 */
	public static final String PRODUCT_CATEGORY_KEY = "vsj_common:product_category:";

	/**
	 * 购物车key
	 */
	public static final String SHOPPING_CART_KEY = "vsj_common:shopping_cart:";

	/**
	 *用户登录tokenkey
	 */
	public static final String USER_LOGIN_WECHAT_TOKEN_KEY="users:wechat:user_token:";

	/**
	 * 预订单超时删除
	 */
	public static final String ORDER_LOSE="order:order_lose:@";

    /**
     * 订单自动签收
     */
    public static final String ORDER_OUT_SIGN="order:out_sign:@";

    /**
     * 订单结算
     */
    public static final String ORDER_SETTLE_ACCOUNTS="order:settle_accounts:@";

    /**
	 * 订单缓存
	 */
	public static final String VSJ_ORDER_CACHE="vsj:order:cache:";

	/**
	 * 系统数据字典
	 */
	public static final String SYS_DICTIONARY_KEY = "vsj_common:sys_dictionary:";
    /**
     * token
     */
    public static final String USER_TOKEN="vsj_token:user_token:";


    /**===================================微信素材小程序redis-key===================================================*/

	/**
	 * 微信素材小程序-系统配置
	 */
	public static final String VSJ_MATERIAL_SYS_CONFIG_KEY = "vsj_material:common:sys_%s_config:";

	/**
	 * 微信素材小程序-分类key
	 */
	public static final String VSJ_MATERIAL_CATEGORY_KEY = "vsj_material:common:category:platformcode:";
}
