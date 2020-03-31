package com.vsj.material.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SysConfigConstants
 * @Description 系统配置常量
 * @Date 2019/8/13 11:10
 * @Created by wangzx
 */
@Data
public class SysConfigConstants implements Serializable {

    /**==================================================系统配置start=============================================================*/

    /**
     * 系统配置-头部标题
     */
    public static final String SYS_HEADER_TITLE = "sys_header_title";

    /**
     * 系统配置-分享标题
     */
    public static final String SYS_SHARE_TITLE = "sys_share_title";

    /**
     * 系统配置-分享描述
     */
    public static final String SYS_SHARE_DESCRIPTION = "sys_share_description";

    /**
     * 系统配置-分享图片
     */
    public static final String SYS_SHARE_IMAGE = "sys_share_image";

    /**
     * 系统配置-指定用户名
     */
    public static final String SYS_SPECIFY_USERNAME = "sys_specify_username";

    /**
     * 系统配置-指定用户头像
     */
    public static final String SYS_SPECIFY_HEADER = "sys_specify_header";

    /**
     * 系统配置-素材头像昵称
     */
    public static final String SYS_MATERIAL_HEADER = "sys_material_header";

    /**
     * 系统配置-强制绑定手机 0=不强制;1=强制
     */
    public static final String SYS_BUILDING_PHONE = "sys_building_phone";

    /**
     * 系统配置组
     */
    public static final String[] SYS_GROUP ={SYS_HEADER_TITLE,SYS_SHARE_TITLE,SYS_SHARE_DESCRIPTION,SYS_SHARE_IMAGE,
            SYS_SPECIFY_USERNAME, SYS_SPECIFY_HEADER,SYS_MATERIAL_HEADER,SYS_BUILDING_PHONE};

    /**==================================================系统配置end=============================================================*/

    /**==================================================商城装修配置start=============================================================*/

    /**
     * 商城装修-是否显示菜单导航 0=不显示;1=显示
     */
    public static final String MALL_SHOW_NAVIGATION_MENU = "mall_show_navigation_menu";

    /**
     * 商城装修配置组
     */
    public static final String[] SHOPPING_GROUP ={MALL_SHOW_NAVIGATION_MENU};

    /**==================================================商城装修配置start=============================================================*/


    /**==================================================微信支付配置start=============================================================*/
    /**
     * 微信支付配置-微信支付appId
     */
    public static final String WX_PAY_APPID = "wx_pay_appid";

    /**
     * 微信支付配置-微信支付appSecret
     */
    public static final String WX_PAY_APPSECRET = "wx_pay_appsecret";

    /**
     * 微信支付配置-微信支付mchId
     */
    public static final String WX_PAY_MCHID = "wx_pay_mchid";

    /**
     * 微信支付配置-微信支付apiSecret
     */
    public static final String WX_PAY_APISECRET = "wx_pay_apisecret";

    /**
     * 微信支付配置-微信支付CERT证书文件
     */
    public static final String WX_PAY_CERT_FILE = "wx_pay_cert_file";

    /**
     * 微信支付配置-微信支付KEY密钥文件
     */
    public static final String WX_PAY_KEY_FILE = "wx_pay_key_file";

    /**
     * 微信支付配置组
     */
    public static final String[] WX_PAY_GROUP ={WX_PAY_APPID,WX_PAY_APPSECRET,WX_PAY_APPSECRET,WX_PAY_MCHID,WX_PAY_APISECRET,
            WX_PAY_CERT_FILE,WX_PAY_KEY_FILE};

    /**==================================================微信支付配置start=============================================================*/

    /**==================================================会员等级权限start=============================================================*/
    /**
     * 会员等级配置-浏览素材最低等级
     */
    public static final String USER_BROWSE_MATERIAL_LEVEL = "user_browse_material_level";

    /**
     * 会员等级配置-复制文案最低等级
     */
    public static final String USER_COPY_WRITING_LEVEL = "user_copy_writing_level";

    /**
     * 会员等级配置-一键保存最低等级
     */
    public static final String USER_ONE_CLICK_SAVE_LEVEL = "user_one_click_save_level";

    /**
     * 会员等级配置-收藏最低等级
     */
    public static final String USER_COLLECTION_LEVEL = "user_collection_level";

    /**
     * 会员等级配置-分享最低等级
     */
    public static final String USER_SHARE_LEVEL = "user_share_level";

    /**
     * 会员等级配置-发布素材最低等级
     */
    public static final String USER_RELEASE_MATERIAL_LEVEL = "user_release_material_level";

    /**
     * 会员等级配置-默认级别
     */
    public static final String USER_DEFAULT_LEVEL = "user_default_level";

    /**
     * 绑定手机号级别
     */
    public static final String USER_BUILDING_PHONE_LEVEL = "user_building_phone_level";

    /**
     * 会员体验级别
     */
    public static final String USER_EXPERIENCE_LEVER = "user_experience_level";

    /**
     *会员体验天数
     */
    public static final String USER_EXPERIENCE_DAY = "user_experience_day";

    /**
     * 是否支持系统体验
     */
    public static final String USER_EXPERIENCE_SHOW = "user_experience_show";
    /**
     * 会员等级配置-会员等级配置组
     */
    public static final String[] LEVEL_PERMISSION ={USER_BROWSE_MATERIAL_LEVEL,USER_COPY_WRITING_LEVEL,USER_ONE_CLICK_SAVE_LEVEL,
            USER_COLLECTION_LEVEL, USER_SHARE_LEVEL,USER_RELEASE_MATERIAL_LEVEL,USER_DEFAULT_LEVEL,USER_BUILDING_PHONE_LEVEL,
            USER_EXPERIENCE_LEVER,USER_EXPERIENCE_DAY,USER_EXPERIENCE_SHOW};
    /**==================================================会员等级权限start=============================================================*/

    /**==================================================其他第三方API start=============================================================*/
    /**
     * 阿里短信 accesskeyid
     */
    public static final String OTHER_ALI_MESSAGE_ACCESSKEYID = "other_ali_message_accesskeyid";

    /**
     *阿里短信 accesssecret
     */
    public static final String OTHER_ALI_MESSAGE_ACCESSSECRET = "other_ali_message_accesssecret";

    /**
     * 短信签名
     */
    public static final String OTHER_MESSAGE_SIGNATURE = "other_message_signature";

    /**
     * 注册模板编号
     */
    public static final String OTHER_REGISTER_NO = "other_register_no";

    /**
     * 找回模板编号
     */
    public static final String OTHER_RETRIEVE_NO = "other_retrieve_no";

    /**
     * 远程文件存储
     */
    public static final String OTHER_FILE_STORAGE = "other_file_storage";
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
     * OSS_FILE_PATH
     */
    public static final String OSS_FILE_PATH = "oss_file_path";
    /**
     * OSS_IMAGE_URI
     */
    public static final String OSS_IMAGE_URI = "oss_image_uri";

    public static final String[] OTHER_API = {OTHER_ALI_MESSAGE_ACCESSKEYID,OTHER_ALI_MESSAGE_ACCESSSECRET,OTHER_MESSAGE_SIGNATURE,
            OTHER_REGISTER_NO,OTHER_RETRIEVE_NO,OTHER_FILE_STORAGE,REMOTE_FILES,ACCESS_KEY_ID,ACCESS_KEY_SECRET,BUCKET_NAME,OSS_OPTION,OSS_ENDPOINT,OSS_FILE_PATH,OSS_IMAGE_URI };
    /**==================================================其他第三方API end=============================================================*/
}
