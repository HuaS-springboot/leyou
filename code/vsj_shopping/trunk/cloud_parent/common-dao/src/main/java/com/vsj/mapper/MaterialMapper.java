package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjMaterial;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.wechat.MaterialList;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author: HuaS
 * @Date :2019/8/8 9:59
 * @Describe:
 */
@Mapper
public interface MaterialMapper {
    public static final String  tableName = "vsj_material";

    @SelectProvider(type = MaterialMapper.MaterialProvider.class,method="getMaterialList")
    List<VsjMaterial> getMaterialList();

    @InsertProvider(type = MaterialMapper.MaterialProvider.class,method="insertMaterial")
    int insertMaterial(VsjMaterial vsjMaterial);

    @UpdateProvider(type = MaterialMapper.MaterialProvider.class,method="updateMaterial")
    int updateMaterial (VsjMaterial vsjMaterial);

    @DeleteProvider(type = MaterialMapper.MaterialProvider.class,method="deleteMaterial")
    int deleteMaterial(VsjMaterial vsjMaterial);

    @UpdateProvider(type= MaterialMapper.MaterialProvider.class,method="updataSortList")
    int updataSortList(MaterialList materialList);

    class  MaterialProvider{
        public String updataSortList(MaterialList materialList){
            String sql = new SQL(){{
                UPDATE("vsj_material");
                WHERE("id= #{id} ");
            }}.toString();
            return sql;
        }

        public String deleteMaterial(VsjMaterial vsjMaterial){
            String  sql = new SQL(){{
                DELETE_FROM("vsj_material");
                WHERE("id= #{id}");
//                String where = whereBuild(vsjMaterial);
//                if(StringUtil.isNoEmptyStr(where)){
//                    WHERE(where);
//                }
            }}.toString();
            return sql;
        }

        public String whereBuild(VsjMaterial vsjMaterial){
            List<String> list = new ArrayList<>();
            if(vsjMaterial.getId()!=null){
                list.add("id= #{id}");
            }
//            if(vsjMaterial.getPlatformCode()!=null){
//                list.add("platform_code = #{platformCode}");
//            }
            return String.join("and", list);
        }

        public String updateMaterial(VsjMaterial vsjMaterial){
            String sql = new SQL(){{
                UPDATE(tableName);
                if (StringUtil.isNoEmptyStr(vsjMaterial.getImg())){
                    SET("img = #{img}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterial.getContent())){
                    SET("content = #{content}");
                }
                if(vsjMaterial.getSort() != null){
                    SET("sort = #{sort}");
                }
                if(vsjMaterial.getProductId() != null){
                    SET("product_id =#{productId}");
                }
                if(vsjMaterial.getPlatformCode() != null){
                    SET("platform_code = #{platformCode}");
                }
                WHERE("id= #{id} and platform_code = #{platformCode}");

            }}.toString();
            return sql;
        }

        public String insertMaterial(VsjMaterial vsjMaterial){
            String sql = new SQL(){{
                INSERT_INTO("vsj_material");
                if(StringUtil.isNoEmptyStr(vsjMaterial.getContent())){
                    VALUES("content","#{content}");
                }
                if(StringUtil.isNoEmptyStr(vsjMaterial.getImg())){
                    VALUES("img","#{img}");
                }
                if(vsjMaterial.getPlatformCode()!=null){
                    VALUES("platform_code","#{platformCode}");
                }
                if(vsjMaterial.getProductId()!=null){
                    VALUES("product_id","#{productId}");
                }
                VALUES("create_time","NOW()");
                if(vsjMaterial.getSort()!=null){
                    VALUES("sort","#{sort}");
                }
            }}.toString();
            return sql;
        }

        public String getMaterialList(){
            String sql = new SQL(){{
                SELECT("m.id,m.content,m.img,m.platform_code,m.sort,m.create_time,p.product_name,m.product_id");
                FROM("vsj_material m");
                LEFT_OUTER_JOIN("vsj_product p ON m.product_id = p.product_id");
                ORDER_BY("m.sort DESC");
            }}.toString();
            return sql;
        }
    }
}
