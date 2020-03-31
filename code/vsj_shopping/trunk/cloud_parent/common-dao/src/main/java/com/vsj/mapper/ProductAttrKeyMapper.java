package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjProductAttrKey;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.ProductAttrResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @Classname ProductAttrKeyMapper
 * @Description TODO
 * @Date 2019/7/25 17:38
 * @Created by wangzx
 */
@Mapper
public interface ProductAttrKeyMapper {

    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @InsertProvider(type = ProductAttrKeyMapper.ProductAttrKeyProvider.class,method = "insertProductAttrKey")
    int insertProductAttrKey(VsjProductAttrKey vsjProductAttrKey);

    @Results({
            @Result(id=true,column = "id",property="id"),
            @Result(column="{attrId = id, platformCode = platform_code}",property="attrValueList",
                    many=@Many(
                            select = "com.vsj.mapper.ProductAttrValueMapper.getAttrValueList"
                    ))
    })
    @SelectProvider(type = ProductAttrKeyMapper.ProductAttrKeyProvider.class,method = "getAttrList")
    List<ProductAttrResponse> getAttrList(BaseQueryStat queryStat);

    class ProductAttrKeyProvider{

        public String insertProductAttrKey(VsjProductAttrKey vsjProductAttrKey){
            String sql = new SQL(){{
                INSERT_INTO("vsj_product_attr_key");
                if(StringUtil.isNoEmptyStr(vsjProductAttrKey.getAttrName())){
                    VALUES("attr_name","#{attrName}");
                }
                if(vsjProductAttrKey.getNameSort() != null){
                    VALUES("name_sort","#{nameSort}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String getAttrList(){
            String sql = new SQL(){{
                SELECT("id,attr_name,create_time,platform_code");
                FROM("vsj_product_attr_key");
                WHERE("platform_code = #{platformCode}");
                ORDER_BY("name_sort DESC");
            }}.toString();
            return sql;
        }
    }
}
