package com.vsj.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.model.VsjProductAttrValue;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @Classname ProductAttrValueMapper
 * @Description TODO
 * @Date 2019/7/25 17:43
 * @Created by wangzx
 */
@Mapper
public interface ProductAttrValueMapper {

    @InsertProvider(type = ProductAttrValueMapper.ProductAttrValueProvider.class,method = "insertAttrValue")
    int insertAttrValue(VsjProductAttrValue v);

    @SelectProvider(type = ProductAttrValueMapper.ProductAttrValueProvider.class,method = "getAttrValueList")
    List<VsjProductAttrValue> getAttrValueList(@Param("attrId") Integer attrId,@Param("platformCode") Integer platformCode);

    class ProductAttrValueProvider{
        public String insertAttrValue(VsjProductAttrValue v){
            String sql = new SQL(){{
                INSERT_INTO("vsj_product_attr_value");
                if(v.getAttrId() != null){
                    VALUES("attr_id","#{attrId}");
                }
                if(StringUtil.isNoEmptyStr(v.getAttrValue())){
                    VALUES("attr_value","#{attrValue}");
                }
                if(v.getValueSort() != null){
                    VALUES("value_sort","#{valueSort}");
                }
                VALUES("create_time","NOW()");
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String getAttrValueList(Integer attrId,Integer platformCode){
            String sql = new SQL(){{
                SELECT("id,attr_id,attr_value,value_sort,create_time");
                FROM("vsj_product_attr_value");
                WHERE("attr_id = #{attrId} and platform_code = #{platformCode}");
                ORDER_BY("value_sort DESC");
            }}.toString();
            return sql;
        }
    }
}
