package com.vsj.mapper;

import com.vsj.model.VsjSaasSystem;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Classname SaasSystemMapper
 * @Description TODO
 * @Date 2019/7/31 15:21
 * @Created by wangzx
 */
@Mapper
public interface SaasSystemMapper {

    @InsertProvider(type =SaasSystemMapper.SaasSystemProvider.class,method = "insertSaasSystem")
    int insertSaasSystem(VsjSaasSystem vsjSaasSystem);

    class SaasSystemProvider{

        public String insertSaasSystem(VsjSaasSystem vsjSaasSystem){
            String sql = new SQL(){{
                INSERT_INTO("vsj_saas_system");
                if(vsjSaasSystem.getSaasName() != null){
                    VALUES("saas_name","#{saasName}");
                }
                if(vsjSaasSystem.getSaasDescribe() != null){
                    VALUES("saas_describe","#{saasDescribe}");
                }
                if(vsjSaasSystem.getSaasId() != null){
                    VALUES("saas_id","#{saasId}");
                }
                if(vsjSaasSystem.getSaasType() != null){
                    VALUES("saas_type","#{saasType}");
                }
                if(vsjSaasSystem.getAppId() != null){
                    VALUES("app_id","#{appId}");
                }
                if(vsjSaasSystem.getAppSecret() != null){
                    VALUES("app_secret","#{appSecret}");
                }
                if(vsjSaasSystem.getHeadPortrait() != null){
                    VALUES("head_portrait","#{headPortrait}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

    }
}
