package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjProductCategory;

import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/7/29 13:57
 * @Describe:
 */
@Mapper
public interface ProductCategoryMapper {

    @UpdateProvider(type=ProductCategoryMapper.ProductCategory.class,method="updateProductCategory")
    int updateProductCategory(VsjProductCategory vsjProductCategory);

    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @InsertProvider(type=ProductCategoryMapper.ProductCategory.class,method="insertProductCategory")
    int insertProductCategory(VsjProductCategory vsjProductCategory);

    @SelectProvider(type=ProductCategoryMapper.ProductCategory.class,method="findProductCategory")
    List<VsjProductCategory> findProductCategory(BaseQueryStat queryStat);

    @DeleteProvider(type = ProductCategoryMapper.ProductCategory.class,method="deleteProductCategory")
    int deleteProductCategory(VsjProductCategory vsjProductCategory);

    @SelectProvider(type = ProductCategoryMapper.ProductCategory.class,method="selectProductCategoryDetails")
    List<VsjProductCategory> selectProductCategoryDetails(VsjProductCategory vsjProductCategory);


    class ProductCategory{

        public String selectProductCategoryDetails(VsjProductCategory vsjProductCategory){
            String sql = new SQL(){{
                SELECT("c.id,c.category_name,c.category_code,c.parent_id,c.category_sort");
                SELECT("c.category_status,c.modified_time,c.ico,c.platform_code");
                FROM("vsj_product_category c");
                String  where = whereBuild(vsjProductCategory);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
//                WHERE("id = #{id} and  parent_id = #{parentId}");
            }}.toString();
            return sql;
        }

        public String deleteProductCategory(VsjProductCategory vsjProductCategory){
            String sql = new SQL(){{
                DELETE_FROM("vsj_product_category");
                String  where = whereBuild(vsjProductCategory);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
            }}.toString();
            return sql;
        }

        public String findAllProductCategory(BaseQueryStat queryStat){
            String sql = new SQL(){{
                SELECT("c.id,c.category_name,c.category_code,c.parent_id,c.category_sort");
                SELECT("c.category_status,c.modified_time,c.ico,c.platform_code");
                FROM("vsj_product_category c");
            }}.toString();
            return sql;
        }

        public String findProductCategory(BaseQueryStat queryStat){
            String sql = new SQL(){{
                SELECT("c.id,c.category_name,c.category_code,c.parent_id,c.category_sort");
                SELECT("c.category_status,c.modified_time,c.ico,c.platform_code");
                FROM("vsj_product_category c");
                WHERE("c.category_status != 0 and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String insertProductCategory(VsjProductCategory vsjProductCategroy){
            String sql = new SQL(){{
                INSERT_INTO("vsj_product_category");
                if(StringUtil.isNoEmptyStr(vsjProductCategroy.getCategoryName())){
                    VALUES("category_name","#{categoryName}");
                }
//                if(StringUtil.isNoEmptyStr(vsjProductCategroy.getCategoryCode())){
//                    VALUES("category_code","#{categoryCode}");
//                }
//                if(vsjProductCategroy.getTypeName()!=null){
//                    VALUES("type_name","#{typeName}");
//                }
                if(vsjProductCategroy.getParentId()!=null){
                    VALUES("parent_id","#{parentId}");
                }
                if(vsjProductCategroy.getCategorySort()!=null){
                    VALUES("category_sort","#{categorySort}");
                }
                if(vsjProductCategroy.getCategoryStatus()!=null){
                    VALUES("category_status","#{categoryStatus}");
                }
                VALUES("modified_time","NOW()");
                if(StringUtil.isNoEmptyStr(vsjProductCategroy.getIco())){
                    VALUES("ico","#{ico}");
                }
                if(vsjProductCategroy.getPlatformCode()!=null){
                    VALUES("platform_code","#{platformCode}");
                }
            }}.toString();
            return sql;
        }

        public String whereBuild(VsjProductCategory vsjProductCategory){
            List<String> list = new ArrayList<>();
            if(vsjProductCategory.getId()!=null){
                list.add("id = #{id}");
            }
            if(vsjProductCategory.getPlatformCode()!= null){
                list.add("platform_code = #{platformCode}");
            }
            if(vsjProductCategory.getParentId()!=null){
                list.add("parent_id = #{parentId}");
            }
            return String.join(" and ",list);
        }

        public String updateProductCategory(VsjProductCategory vsjProductCategroy){
            String sql = new SQL(){{
                UPDATE("vsj_product_category");
                if(StringUtil.isNoEmptyStr(vsjProductCategroy.getCategoryName())){
                    SET("category_name = #{categoryName}");
                }
                if(StringUtil.isNoEmptyStr(vsjProductCategroy.getCategoryCode())){
                    SET("category_code = #{categoryCode}");
                }
                if(vsjProductCategroy.getParentId()!=null){
                    SET("parent_id = #{parentId}");
                }
                if(vsjProductCategroy.getCategorySort()!=null){
                    SET("category_sort = #{categorySort}");
                }
                if(vsjProductCategroy.getCategoryStatus()!=null){
                    SET("category_status = #{categoryStatus}");
                }
                SET("modified_time = NOW()");
                if(StringUtil.isNoEmptyStr(vsjProductCategroy.getIco())){
                    SET("ico = #{ico}");
                }
                if(vsjProductCategroy.getPlatformCode()!=null){
                    SET("platform_code = #{platformCode}");
                }
                String where = whereBuild(vsjProductCategroy);
                if(StringUtil.isNoEmptyStr(where)){
                    WHERE(where);
                }
            }}.toString();
            return sql;
        }
    }

}
