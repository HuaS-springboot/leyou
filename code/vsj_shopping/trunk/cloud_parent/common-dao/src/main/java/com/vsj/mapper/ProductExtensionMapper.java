package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjProductExtension;

import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @Classname ProductExtensionMapper
 * @Description TODO
 * @Date 2019/7/24 18:31
 * @Created by wangzx
 */
@Mapper
public interface ProductExtensionMapper {

    @InsertProvider(type = ProductExtensionMapper.ProductExtensionProvider.class,method = "insertProductExtension")
    int insertProductExtension(VsjProductExtension vsjProductExtension);

    @SelectProvider(type = ProductExtensionMapper.ProductExtensionProvider.class,method = "getExtensionList")
    List<VsjProductExtension> getExtensionList(BaseQueryStat queryStat);

    @UpdateProvider(type = ProductExtensionMapper.ProductExtensionProvider.class,method = "updateExtension")
    int updateExtension(VsjProductExtension vsjProductExtension);

    @DeleteProvider(type = ProductExtensionMapper.ProductExtensionProvider.class,method = "deleteExtension")
    int deleteExtension(BaseQueryStat queryStat);

    class ProductExtensionProvider{
        public String insertProductExtension(VsjProductExtension vsjProductExtension){
            String sql = new SQL(){{
                INSERT_INTO("vsj_product_extension");
                if(vsjProductExtension.getProductId() != null){
                    VALUES("product_id","#{productId}");
                }
                if(vsjProductExtension.getType() != null){
                    VALUES("type","#{type}");
                }
                if(StringUtil.isNoEmptyStr(vsjProductExtension.getExtensionKey())){
                    VALUES("extension_key","#{extensionKey}");
                }
                if(StringUtil.isNoEmptyStr(vsjProductExtension.getExtensionValue())){
                    VALUES("extension_value","#{extensionValue}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String getExtensionList(BaseQueryStat queryStat){
            String sql = new SQL(){{
                SELECT("id,product_id,type,extension_key,extension_value,create_time");
                FROM("vsj_product_extension");
                WHERE("product_id = #{id} and platform_code = #{platformCode}");
                ORDER_BY("id DESC");
            }}.toString();
            return sql;
        }

        public String updateExtension(VsjProductExtension vsjProductExtension){
            String sql = new SQL(){{
                UPDATE("vsj_product_extension");
                if(StringUtil.isNoEmptyStr(vsjProductExtension.getExtensionKey())){
                    SET("extension_key = #{extensionKey}");
                }
                if(StringUtil.isNoEmptyStr(vsjProductExtension.getExtensionValue())){
                    SET("extension_value = #{extensionValue}");
                }
                if(vsjProductExtension.getType() != null){
                    SET("type = #{type}");
                }
                WHERE("id = #{id} AND platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String deleteExtension(BaseQueryStat queryStat){
            String sql = new SQL(){{
                DELETE_FROM("vsj_product_extension");
                WHERE("id = #{id} AND platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
