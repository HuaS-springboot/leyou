package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjProductImg;

import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProductImgMapper
 * @Description TODO
 * @Date 2019/7/29 9:29
 * @Created by wangzx
 */
@Mapper
public interface ProductImgMapper {

    @InsertProvider(type = ProductImgMapper.ProductImgProvider.class,method = "insertProductImage")
    int insertProductImage(VsjProductImg productImg);

    @SelectProvider(type = ProductImgMapper.ProductImgProvider.class,method = "getProductImageList")
    List<VsjProductImg> getProductImageList(BaseQueryStat queryStat);

    @UpdateProvider(type = ProductImgMapper.ProductImgProvider.class,method = "updateProductImage")
    int updateProductImage(VsjProductImg vsjProductImg);

    @DeleteProvider(type = ProductImgMapper.ProductImgProvider.class,method = "deleteProductImage")
    int deleteProductImage(BaseQueryStat queryStat);

    @Select("select id,product_id,pic_url,platform_code from vsj_product_img where product_id= #{productId} and platform_code = #{platformCode}")
    VsjProductImg getProductImageByProductId(Integer productId, Integer platformCode);

    @Delete("delete from vsj_product_img where is_master = #{type} and platform_code = #{platformCode}")
    int deleteProductImageByType(@Param("type") Integer type, @Param("platformCode") Integer platformCode);

    @Select("select id,product_id,pic_url,is_master,platform_code from vsj_product_img where product_id= #{productId} and platform_code = #{platformCode}")
    List<VsjProductImg> getMasteImage(Integer productId, Integer platformCode);

    class ProductImgProvider{

        public String insertProductImage(VsjProductImg productImg){
            String sql = new SQL(){{
                INSERT_INTO("vsj_product_img");
                if(productImg.getProductId() != null){
                    VALUES("product_id","#{productId}");
                }
                if(productImg.getPicDesc() != null){
                    VALUES("pic_desc","#{picDesc}");
                }
                if(productImg.getPicUrl() != null){
                    VALUES("pic_url","#{picUrl}");
                }
                if(productImg.getIsMaster() != null){
                    VALUES("is_master","#{isMaster}");
                }
                if(productImg.getPicOrder() != null){
                    VALUES("pic_order","#{picOrder}");
                }
                if(productImg.getPicStatus() != null){
                    VALUES("pic_status","#{picStatus}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String getProductImageList(BaseQueryStat queryStat){
            String sql = new SQL(){{
                SELECT("id,product_id,pic_desc,pic_url,is_master,pic_order,pic_status,create_time");
                FROM("vsj_product_img");
                String where = whereBuilder(queryStat);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
                ORDER_BY("pic_order DESC");
            }}.toString();
            return sql;
        }

        private String whereBuilder(BaseQueryStat queryStat){
            List<String> list = new ArrayList<>();
            list.add("platform_code = #{platformCode}");
            if(queryStat.getStatus() != null){
                list.add("pic_status = #{status}");
            }
            if(queryStat.getType() != null){
                list.add("is_master = #{type}");
            }
            return String.join(" and ", list);
        }

        public String updateProductImage(VsjProductImg vsjProductImg){
            String sql = new SQL(){{
                UPDATE("vsj_product_img");
                if(vsjProductImg.getProductId() != null){
                    SET("product_id = #{productId}");
                }
                if(vsjProductImg.getPicDesc() != null){
                    SET("pic_desc = #{picDesc}");
                }
                if(vsjProductImg.getPicUrl() != null){
                    SET("pic_url = #{picUrl}");
                }
                if(vsjProductImg.getIsMaster() != null){
                    SET("is_master = #{isMaster}");
                }
                if(vsjProductImg.getPicOrder() != null){
                    SET("pic_order = #{picOrder}");
                }
                if(vsjProductImg.getPicStatus() != null){
                    SET("pic_status = #{picStatus}");
                }
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String deleteProductImage(BaseQueryStat queryStat){
            String sql = new SQL(){{
                DELETE_FROM("vsj_product_img");
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
