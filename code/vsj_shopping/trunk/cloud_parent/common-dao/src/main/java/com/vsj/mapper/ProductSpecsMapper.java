package com.vsj.mapper;

import com.vsj.model.VsjProductSpecs;

import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @Classname ProductSpecsMapper
 * @Description TODO
 * @Date 2019/7/26 14:10
 * @Created by wangzx
 */
@Mapper
public interface ProductSpecsMapper {

    @InsertProvider(type = ProductSpecsMapper.ProductSpecsProvider.class,method = "insertProductSpecs")
    int insertProductSpecs(VsjProductSpecs s);

    @SelectProvider(type = ProductSpecsMapper.ProductSpecsProvider.class,method = "getProductSpecsList")
    List<VsjProductSpecs> getProductSpecsList(Integer productId);

    @UpdateProvider(type = ProductSpecsMapper.ProductSpecsProvider.class,method = "updateProductSpecs")
    int updateProductSpecs(VsjProductSpecs vsjProductSpecs);

    @Delete("delete from vsj_product_specs where id = #{id} where platform_code = #{platformCode}")
    int deleteProductSpecs(BaseQueryStat queryStat);

    @Select("SELECT IFNULL(SUM(s.product_stock), 0) FROM vsj_product_specs s WHERE s.product_id = #{productId} " +
            "and s.platform_code = #{platformCode}")
    int getTotalStock(@Param("productId") Integer productId,@Param("platformCode") Integer platformCode);

    @Delete("delete from vsj_product_specs where product_id= #{productId} and platform_code = #{platformCode}")
    int deleteProductSpecsByProductId(@Param("productId") Integer productId,@Param("platformCode") Integer platformCode);

    class ProductSpecsProvider{

        public String insertProductSpecs(VsjProductSpecs s){
            String sql = new SQL(){{
                INSERT_INTO("vsj_product_specs");
                VALUES("product_id","#{productId}");
                VALUES("attr_json","#{attrJson}");
                if(s.getProductStock() != null){
                    VALUES("product_stock","#{productStock}");
                }
                if(s.getProductPrice() != null){
                    VALUES("product_price","#{productPrice}");
                }
                if(s.getIsnullSell() != null){
                    VALUES("isnull_sell","#{isnullSell}");
                }
                if(s.getSaleNum() != null){
                    VALUES("sale_num","#{saleNum}");
                }
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String getProductSpecsList(Integer productId){
            String sql = new SQL(){{
                SELECT("s.id,s.product_id,s.product_stock,s.product_price,s.isnull_sell,s.sale_num");
                SELECT("s.attr_json");
                FROM("vsj_product_specs s");
                WHERE("s.product_id = #{productId}");
            }}.toString();
            return sql;
        }

        public String updateProductSpecs(VsjProductSpecs vsjProductSpecs){
            String sql = new SQL(){{
                UPDATE("vsj_product_specs");
                if(vsjProductSpecs.getProductId() != null){
                    SET("product_id = #{productId}");
                }
                if(vsjProductSpecs.getProductStock() != null){
                    SET("product_stock = #{productStock}");
                }
                if(vsjProductSpecs.getProductPrice() != null){
                    SET("product_price = #{productPrice}");
                }
                if(vsjProductSpecs.getAttrJson() != null){
                    SET("attr_json = #{attrJson}");
                }
                if(vsjProductSpecs.getIsnullSell() != null){
                    SET("isnull_sell = #{isnullSell}");
                }
                if(vsjProductSpecs.getSaleNum() != null){
                    SET("sale_num = #{saleNum}");
                }
                SET("modified_time = NOW()");
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
