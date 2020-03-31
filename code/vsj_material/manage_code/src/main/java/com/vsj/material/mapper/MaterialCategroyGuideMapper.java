package com.vsj.material.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.MaterialCategroyGuide;
import com.vsj.material.model.request.QueryStat;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @Author: sxm
 * @Date :2019/8/16 17:17
 * @Describe: 分类导航mapper
 */
@Mapper
public interface MaterialCategroyGuideMapper {

    @SelectProvider(type = MaterialCategroyGuideMapper.MaterialCategroyGuideProvider.class,method = "getMaterialCategroyGuide")
    List<MaterialCategroyGuide> getMaterialCategroyGuide(QueryStat queryStat);

    @DeleteProvider(type = MaterialCategroyGuideMapper.MaterialCategroyGuideProvider.class,method ="deleteMaterialCategroyGuide")
    int deleteMaterialCategroyGuide(QueryStat queryStat);

    @UpdateProvider(type = MaterialCategroyGuideMapper.MaterialCategroyGuideProvider.class,method = "updateMaterialCategroyGuide")
    int updateMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide);

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @InsertProvider(type = MaterialCategroyGuideMapper.MaterialCategroyGuideProvider.class,method = "insertMaterialCategroyGuide")
     int insertMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide);
    class MaterialCategroyGuideProvider{

        public String getMaterialCategroyGuide(QueryStat queryStat){
            String sql=new SQL(){{
                SELECT("*");
                FROM("vsj_material_categroy_guide");
                WHERE("platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String deleteMaterialCategroyGuide(QueryStat queryStat){
            String sql = new SQL(){{
                DELETE_FROM("vsj_material_categroy_guide");
                WHERE("FIND_IN_SET(id,#{ids}) and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }

        public String updateMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide){
            String sql = new SQL(){{
               UPDATE("vsj_material_categroy_guide");
              if (StringUtil.isNoEmptyStr(materialCategroyGuide.getImageUrl())){
                  SET("image_url = #{imageUrl}");
              }
              if(materialCategroyGuide.getCategoryId()!=null){
                  SET("category_id = #{categoryId}");
              }
              WHERE("platform_code = #{platformCode} and id = #{id}");
            }}.toString();
            return sql;
        }

        public String insertMaterialCategroyGuide(MaterialCategroyGuide materialCategroyGuide){
            String sql= new SQL(){{
                INSERT_INTO("vsj_material_categroy_guide");
                if(StringUtil.isNoEmptyStr(materialCategroyGuide.getImageUrl())){
                    VALUES("image_url","#{imageUrl}");
                }
                if(materialCategroyGuide.getCategoryId()!=null){
                    VALUES("category_id","#{categoryId}");
                }
                VALUES("id","#{id}");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }
    }
}
