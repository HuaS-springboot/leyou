package com.vsj.common.constants;

/**
 * 
 * @ClassName: SysConfigConstants
 * @Description: 系统配置常量说明
 * @author: mario 
 * @date: 2019年7月26日 下午4:33:47
 * @copyright: 青岛微视角文化传媒有限公司
 */
public interface SysConfigConstants {
	
	/**
	 * 是否开启加载页：0,关闭  1,开启
	 */
	public static final String LOADING_PAGE_SWITCH = "loading_page_switch";
	/**
	 * 载入页图片
	 */
	public static final String LOADING_PAGE_PATH = "loading_page_path";
	
	/**
	 * 商城名称
	 */
	public static final String SHOPPING_NAME = "shopping_name";
	
	/**
	 * 商城logo
	 */
	public static final String SHOPPING_LOGO = "shopping_logo";
	
	/**
	 * 首页是否显示菜单导航
	 */
	public static final String SHOW_NAVIGATION_MENU = "show_navigation_menu";
	
	/**
	 * 奖励模式：详见BountyModeEnum枚举
	 */
	public static final String BOUNTY_MODE = "bounty_mode";
	/**
	 * 未付款订单数量配置
	 * 超过配置数量不让下单
	 */
	public static final String UNPAID_ORDER_NUM ="unpaid_order_num";
	/**
	 * 未付款订单关闭时间（单位：小时）
	 */
	public static final String ORDER_CLOSE_TIME = "order_close_time";
	
	/**
	 * 发货后自动收货天数（单位：天） 
	 */
	public static final String ORDER_CONFIRM_DAY = "order_confirm_day";
	
	/**
	 * 收货后允许退款天数（单位：天）
	 * 超过天数后标志订单结束,奖励金可转为余额
	 */
	public static final String ORDER_ALLOW_REFUND_DAY = "order_allow_refund_day";
	
	/**
	 * 是否强制绑定手机：0,否  1,是
	 */
	public static final String COMPULSORY_BINDING_PHONE = "compulsory_binding_phone";
	
	/**
	 * 系统分享标题
	 */
	public static final String SYS_SHARE_TITLE = "sys_share_title";
	
	/**
	 * 系统分享图片
	 */
	public static final String SYS_SHARE_PIC = "sys_share_pic";

	/**
	 * 系统分享描述
	 */
	public static final String SYS_SHARE_DESC ="sys_share_desc";
	/**
	 *	快递api对接选择	见ExpressAPIEnum枚举 1:快递鸟
	 */
	public static final String SYS_KD_API ="sys_kd_api";
	/**
	 * 快递鸟用户Id
	 */
	public static final String SYS_KDN_USER_ID = "sys_kdn_user_id";
	/**
	 * 快递鸟API key
	 */
	public static final String SYS_KDN_API_KEY = "sys_kdn_api_key";
	
	/**
	 * 微信支付appId
	 */
	public static final String WX_PAY_APPID = "wx_pay_appid";
	
	/**
	 * 微信支付appSecret
	 */
	public static final String WX_PAY_APPSECRET = "wx_pay_appsecret";
	
	/**
	 * 微信支付mchId
	 */
	public static final String WX_PAY_MCHID = "wx_pay_mchid";
	
	/**
	 * 微信支付apiSecret
	 */
	public static final String WX_PAY_APISECRET = "wx_pay_apisecret";
	
	/**
	 * 微信支付CERT证书文件
	 */
	public static final String WX_PAY_CERT_FILE = "wx_pay_cert_file";
	
	/**
	 * 微信支付KEY密钥文件
	 */
	public static final String WX_PAY_KEY_FILE = "wx_pay_key_file";

	/**
	 * 微信企业付款mch_appid
	 */
	public static final String WX_PAY_MCHAPPID = "wx_pay_mch_appid";
	
	/**
	 * 阿里短信ACCESS_KEY_ID
	 */
	public static final String ALI_DX_ACCESS_KEY_ID = "ali_dx_access_key_id";
	
	/**
	 * 阿里短信ACCESS_KEY_SECRET
	 */
	public static final String ALI_DX_ACCESS_KEY_SECRET = "ali_dx_access_key_secret";
	
	/**
	 * 短信签名
	 */
	public static final String ALI_DX_SIGN = "ali_dx_sign";
	
	/**
	 * 注册短信模板
	 */
	public static final String ALI_DX_REGIST_TEMPLATE = "ali_dx_regist_template";
	
	/**
	 * 找回密码短信模板
	 */
	public static final String ALI_DX_FIND_TEMPLATE = "ali_dx_find_template";
	
	/**
	 * 远程文件配置,详见RemoteFilesEnum枚举
	 */
	public static final String REMOTE_FILES = "remote_files";
	
	/**
	 * OSS KEY_ID
	 */
	public static final String ACCESS_KEY_ID = "access_key_id";
	
	/**
	 * OSS KEY_SECRET
	 */
	public static final String ACCESS_KEY_SECRET = "access_key_secret";
	
	/**
	 * OSS BUCKET_NAME
	 */
	public static final String BUCKET_NAME = "bucket_name";
	
	/**
	 * oss配置选项,默认0,为用户信息配置,1为系统托管提供
	 */
	public static final String OSS_OPTION = "oss_option";

	/**
	 * OSS ENDPOINT
	 */
	public static final String OSS_ENDPOINT = "oss_endpoint";



	/**
	 *	素材中心开关,首页是否显示素材中心
	 */
	public static final String MATERIAL_SWITCH = "material_switch";

	/**
	 *	素材中心限制级别Id,权重大于该级别的才可以查看
	 */
	public static final String MATERIAL_LEVEL_ID = "material_level_id";

	/**
	 * 首页配置组
	 */
	public static final String[] SHOPPING_GROUP ={LOADING_PAGE_SWITCH,LOADING_PAGE_PATH,SHOPPING_NAME,SHOPPING_LOGO,SHOW_NAVIGATION_MENU};
	
	/**
	 * 系统配置组
	 */
	public static final String[] SYS_GROUP ={BOUNTY_MODE,ORDER_CLOSE_TIME,ORDER_CONFIRM_DAY,ORDER_ALLOW_REFUND_DAY,COMPULSORY_BINDING_PHONE,SYS_SHARE_TITLE,SYS_SHARE_PIC,SYS_SHARE_DESC};
	
	/**
	 * 微信支付配置组
	 */
	public static final String[] WX_PAY_GROUP ={WX_PAY_APPID,WX_PAY_APPSECRET,WX_PAY_APPSECRET,WX_PAY_MCHID,WX_PAY_APISECRET,WX_PAY_CERT_FILE,WX_PAY_KEY_FILE,WX_PAY_MCHAPPID};
	
	/**
	 * 第三方API配置组
	 */
	public static final String[] OTHER_API_GROUP = {ALI_DX_ACCESS_KEY_ID,ALI_DX_ACCESS_KEY_SECRET,ALI_DX_FIND_TEMPLATE,ALI_DX_REGIST_TEMPLATE,ALI_DX_SIGN};
	
	/**
	 * OSS配置信息
	 */
	public static final String[] OSS_PARAM_GROUP = {ACCESS_KEY_ID,ACCESS_KEY_SECRET,BUCKET_NAME,OSS_OPTION,OSS_ENDPOINT};

	/**
	 * 快递鸟配置信息
	 */
	public static final String[] KDN_PARAM_GROUP = {SYS_KDN_API_KEY,SYS_KDN_USER_ID};

	/**
	 * 素材中心配置组
	 */
	public static final String[] MATERIAL_GROUP ={MATERIAL_SWITCH,MATERIAL_LEVEL_ID};
}
