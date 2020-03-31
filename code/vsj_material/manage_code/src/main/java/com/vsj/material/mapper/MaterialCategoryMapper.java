package com.vsj.material.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.VsjMaterialCategory;
import com.vsj.material.model.request.QueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sxm
 * @Date :2019/8/16 15:14
 * @Describe:后台管理分类
 */
@Mapper
public interface MaterialCategoryMapper {

    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @InsertProvider(type = MaterialCategoryMapper.MaterialCategory.class,method="insertMaterialCategory")
    int insertMaterialCategory(VsjMaterialCategory vsjMaterialCategory);

    @UpdateProvider(type = MaterialCategoryMapper.MaterialCategory.class,method = "editMaterialCategroy")
    int editMaterialCategroy(VsjMaterialCategory vsjMaterialCategory);

    @DeleteProvider(type = MaterialCategoryMapper.MaterialCategory.class,method = "deleteMaterialCategory")
    int deleteMaterialCategory(VsjMaterialCategory vsjMaterialCategory);

    @SelectProvider(type = MaterialCategoryMapper.MaterialCategory.class,method="findMaterialCategory")
    List<VsjMaterialCategory> findMaterialCategory(Integer platformCode);

    @SelectProvider(type = MaterialCategoryMapper.MaterialCategory.class,method = "findMaterialCategoryOne")
    List<VsjMaterialCategory> findMaterialCategoryOne(VsjMaterialCategory vsjMaterialCategory);

    class MaterialCategory{

        public String findMaterialCategoryOne(VsjMaterialCategory vsjMaterialCategory){
            String sql = new SQL(){{
                SELECT("id,cate_name,parent_id");
                SELECT("create_time,sort,platform_code");
                FROM("vsj_material_category ");
                WHERE("parent_id = #{parentId}");
            }}.toString();
            return sql;
        }

        public String findMaterialCategory(Integer platformCode){
            String sql = new SQL(){{
                SELECT("id,cate_name,parent_id");
                SELECT("create_time,sort,platform_code");
                FROM("vsj_material_category ");
                WHERE("platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String deleteMaterialCategory(VsjMaterialCategory vsjMaterialCategory){
            String sql = new SQL(){{
                DELETE_FROM("vsj_material_category");
                WHERE("id = #{id}");
            }}.toString();
            return sql;
        }
        public String insertMaterialCategory(VsjMaterialCategory vsjMaterialCategory){
            String sql = new SQL(){{
                INSERT_INTO("vsj_material_category");
                if(StringUtil.isNoEmptyStr(vsjMaterialCategory.getCateName())){
                    VALUES("cate_name","#{cateName}");
                }
                if(vsjMaterialCategory.getParentId()!=null){
                    VALUES("parent_id","#{parentId}");
                }
                if(vsjMaterialCategory.getSort()!=null){
                    VALUES("sort","#{sort}");
                }
                VALUES("create_time","NOW()");
                if(vsjMaterialCategory.getPlatformCode()!=null){
                    VALUES("platform_code","#{platformCode}");
                }
            }}.toString();
            return sql;
        }
        public String editMaterialCategroy(VsjMaterialCategory vsjMaterialCategory){
            String sql = new SQL(){{
                UPDATE("vsj_material_category");
                if(StringUtil.isNoEmptyStr(vsjMaterialCategory.getCateName())){
                    SET("cate_name = #{cateName}");
                }
                if (vsjMaterialCategory.getParentId()!=null){
                    SET("parent_id = #{parentId}");
                }
                if(vsjMaterialCategory.getSort()!=null){
                    SET("sort = #{sort}");
                }
                if(vsjMaterialCategory.getPlatformCode()!=null){
                    SET("platform_code = #{platformCode}");
                }
                WHERE("id = #{id} and  platform_code = #{platformCode}");//
            }}.toString();
            return sql;
        }

    }

}
