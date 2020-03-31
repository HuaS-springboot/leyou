package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjProduct;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ProductResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProductMapper
 * @Description TODO
 * @Date 2019/7/23 16:38
 * @Created by wangzx
 */
@Mapper
public interface ProductMapper {

    @Options(useGeneratedKeys = true,keyProperty = "productId",keyColumn = "product_id")
    @InsertProvider(type = ProductMapper.ProductProvider.class,method = "insertProduct")
    int insertProduct(VsjProduct vsjProduct);

    @Results({
            @Result(column="{productId = product_id,platformCode = platform_code}",property="totalStock",
                    one = @One(
                            select="com.vsj.mapper.ProductSpecsMapper.getTotalStock"
                    )
            )
    })
    @SelectProvider(type = ProductMapper.ProductProvider.class,method = "getProductList")
    List<VsjProduct> getProductList(BaseQueryStat querySet);

    @UpdateProvider(type = ProductMapper.ProductProvider.class,method = "updateProStatus")
    int updateProStatus(BaseQueryStat queryStat);


    @Results({
            @Result(id=true,column="product_id",property="productId"),
            @Result(column="product_id",property="specsList",
                    many=@Many(
                            select="com.vsj.mapper.ProductSpecsMapper.getProductSpecsList"
                    )
            )
    })
    @SelectProvider(type = ProductMapper.ProductProvider.class,method = "getProductDetail")
    ProductResponse getProductDetail(BaseQueryStat queryStat);

    @UpdateProvider(type = ProductMapper.ProductProvider.class,method = "updateProduct")
    int updateProduct(VsjProduct vsjProduct);


    class ProductProvider{

        public String insertProduct(VsjProduct vsjProduct){
            String sql = new SQL(){{
                INSERT_INTO("vsj_product");
                if(StringUtil.isNoEmptyStr(vsjProduct.getProductName())){
                    VALUES("product_name","#{productName}");
                }
                if(StringUtil.isNoEmptyStr(vsjProduct.getProductImage())){
                    VALUES("product_image","#{productImage}");
                }
                if(vsjProduct.getOneCategoryId() != null){
                    VALUES("one_category_id","#{oneCategoryId}");
                }
                if(vsjProduct.getTwoCategoryId() != null){
                    VALUES("two_category_id","#{twoCategoryId}");
                }
                if(vsjProduct.getSaleTime() != null){
                    VALUES("sale_time","#{saleTime}");
                }
                if(vsjProduct.getIsExchange() != null){
                    VALUES("is_exchange","#{isExchange}");
                }
                if(vsjProduct.getWeight() != null){
                    VALUES("weight","#{weight}");
                }
                if(vsjProduct.getProductSort() != null){
                    VALUES("product_sort","#{productSort}");
                }
                if(vsjProduct.getPublishStatus() != null){
                    VALUES("publish_status","#{publishStatus}");
                }
                if(vsjProduct.getThreeCategoryId() != null){
                    VALUES("three_category_id","#{threeCategoryId}");
                }
                if(StringUtil.isNoEmptyStr(vsjProduct.getShareImage())){
                    VALUES("share_image","#{shareImage}");
                }
                if(StringUtil.isNoEmptyStr(vsjProduct.getShareTitle())){
                    VALUES("share_title","#{shareTitle}");
                }
                if(vsjProduct.getPrice() != null){
                    VALUES("price","#{price}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String getProductList(BaseQueryStat querySet){
            String sql = new SQL(){{
                SELECT("p.sale_num,p.product_sort,p.product_id,p.publish_status,p.price,p.product_name");
                SELECT("p.platform_code");
                SELECT("p.product_image");
                FROM("vsj_product p");
                String where = whereBuilder(querySet);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
                ORDER_BY("p.product_sort DESC");
            }}.toString();
            return sql;
        }

        private String whereBuilder(BaseQueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("platform_code = #{platformCode}");
            if(StringUtil.isNoEmptyStr(queryStat.getTitle())){
                list.add("p.product_name like concat ('%',#{title},'%')");
            }
            if(queryStat.getStatus() != null){
                list.add("p.publish_status = #{status}");
            }else {
                list.add("p.publish_status != -1");
            }
            if(queryStat.getPriceMin() != null){
                list.add("p.price >= #{priceMin}");
            }
            if(queryStat.getPriceMax() != null){
                list.add("p.price <= #{priceMax}");
            }
            if(queryStat.getOneCateId() != null){
                list.add("p.one_category_id = #{oneCateId}");
            }
            if(queryStat.getTwoCateId() != null){
                list.add("p.two_category_id = #{twoCateId}");
            }
            if(queryStat.getThreeCateId() != null){
                list.add("p.three_category_id = #{threeCateId}");
            }
            return String.join(" and ", list);
        }

        public String updateProStatus(BaseQueryStat querySet){
            String sql = new SQL(){{
                UPDATE("vsj_product");
                SET("publish_status = #{status}");
                WHERE("FIND_IN_SET(product_id,#{ids}) AND platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String getProductDetail(BaseQueryStat querySet){
            String sql = new SQL(){{
                SELECT("DISTINCT p.product_id,p.product_code,p.product_name,p.product_image,p.one_category_id,p.supplier_id");
                SELECT("p.price,p.publish_status,p.audit_status,p.sale_num,p.sale_time,p.describesion,p.weight,p.is_exchange");
                SELECT("p.three_category_id,p.share_image,p.share_title,p.two_category_id,p.describesion,p.template_id");
                FROM("vsj_product p");
                WHERE("p.product_id = #{id} and p.platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String updateProduct(VsjProduct vsjProduct){
            String sql = new SQL(){{
                UPDATE("vsj_product");
                if(StringUtil.isNoEmptyStr(vsjProduct.getProductName())){
                    SET("product_name = #{productName}");
                }
                if(StringUtil.isNoEmptyStr(vsjProduct.getProductCode())){
                    SET("product_code = #{productCode}");
                }
                if(StringUtil.isNoEmptyStr(vsjProduct.getProductImage())){
                    SET("product_image = #{productImage}");
                }
                if(vsjProduct.getBrandId() != null){
                    SET("brand_id = #{brandId}");
                }
                if(vsjProduct.getSupplierId() != null){
                    SET("supplier_id = #{supplierId}");
                }
                if(vsjProduct.getOneCategoryId() != null){
                    SET("one_category_id = #{oneCategoryId}");
                }
                if(vsjProduct.getTwoCategoryId() != null){
                    SET("two_category_id = #{twoCategoryId}");
                }
                if(vsjProduct.getThreeCategoryId() != null){
                    SET("three_category_id = #{threeCategoryId}");
                }
                if(vsjProduct.getPrice() != null){
                    SET("price = #{price}");
                }
                if(vsjProduct.getSaleTime() != null){
                    SET("sale_time = #{saleTime}");
                }
                if(vsjProduct.getIsExchange() != null){
                    SET("is_exchange = #{isExchange}");
                }
                if(vsjProduct.getWeight() != null){
                    SET("weight = #{weight}");
                }
                if(vsjProduct.getProductSort() != null){
                    SET("product_sort = #{productSort}");
                }
                if(vsjProduct.getPublishStatus() != null){
                    SET("publish_status = #{publishStatus}");
                }
                if(StringUtil.isNoEmptyStr(vsjProduct.getDescribesion())){
                    SET("describesion = #{describesion}");
                }
                if(vsjProduct.getIsDealer() != null){
                    SET("is_dealer = #{isDealer}");
                }
                if(vsjProduct.getIsCommission() != null){
                    SET("is_commission = #{isCommission}");
                }
                if(vsjProduct.getTemplateId() != null){
                    SET("template_id = #{templateId}");
                }
                SET("modified_time = NOW()");
                WHERE("product_id = #{productId} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
