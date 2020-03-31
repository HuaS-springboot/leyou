package com.vsj.mapper;


import com.vsj.common.utils.StringUtil;

import com.vsj.model.VsjShoppingCart;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/7/30 13:32
 * @Describe:
 */
@Mapper
public interface ShoppingCartMapper {

    @SelectProvider(type= ShoppingCartMapper.ShoppingCartSQL.class,method="selectShoppingCartList")
    List<VsjShoppingCart> selectShoppingCartList(Integer regId, Integer platformCode);

    @InsertProvider(type = ShoppingCartMapper.ShoppingCartSQL.class,method="insertShoppingCart")
    int insertShoppingCart(VsjShoppingCart vsjShoppingCart);

    @DeleteProvider(type = ShoppingCartMapper.ShoppingCartSQL.class,method="deleteShoppingCart")
    int deleteShoppingCart(@Param("cartId") Integer cartId, @Param("platformCode") Integer platformCode);


    class ShoppingCartSQL{
        public String insertShoppingCart(VsjShoppingCart vsjShoppingCart){
            String sql = new SQL(){{
                INSERT_INTO("vsj_shopping_cart");
                if(StringUtil.isNoEmptyStr(vsjShoppingCart.getAttrJson())){
                    VALUES("attr_json","#{attrJson}");
                }
                if(vsjShoppingCart.getCartId()!=null){
                    VALUES("cart_id","#{cartId}");
                }
                if(vsjShoppingCart.getIsProductExists()!= null){
                    VALUES("is_product_exists","#{isProductExists}");
                }
                if(vsjShoppingCart.getNumber()!=null){
                    VALUES("number","#{number}");
                }
                if(vsjShoppingCart.getProductSpecsId()!=null){
                    VALUES("product_specs_id","#{productSpecsId}");
                }
                if(vsjShoppingCart.getPlatformCode()!=null){
                    VALUES("platform_code","#{platformCode}");
                }
                if (vsjShoppingCart.getRegId()!=null){
                    VALUES("reg_id","#{regId}");
                }
                if(vsjShoppingCart.getProductId()!=null){
                    VALUES("product_id","#{productId}");
                }
                if(vsjShoppingCart.getShopId()!=null){
                    VALUES("shop_id","#{shopId}");
                }
                VALUES("created_time","NOW()");
            }}.toString();
            return sql;
        }

        public String selectShoppingCartList(Integer regId, Integer platformCode){
            String sql = new SQL(){{
                SELECT("s.cart_id,s.created_time,s.is_product_exists,s.number,s.platform_code,s.product_id,s.product_specs_id,s.reg_id,s.shop_id");
                SELECT("p.product_name,p.product_image,p.price");
                SELECT("ps.id,ps.attr_json");
                FROM("vsj_shopping_cart s");
                LEFT_OUTER_JOIN("vsj_product_specs ps ON s.product_specs_id = ps.id");
                LEFT_OUTER_JOIN("vsj_product p ON s.product_id = p.product_id");
                LEFT_OUTER_JOIN("vsj_register r ON s.reg_id = r.id");
                LEFT_OUTER_JOIN("vsj_shop sh on s.shop_id = sh.shop_id");
                WHERE("s.reg_id = #{regId} and s.platform_code = #{platformCode}");// and s.platform_code = #{platformCode}
            }}.toString();
            return sql;
        }

        private String whereBuile (VsjShoppingCart vsjShoppingCart){
            List<String> list = new ArrayList<>();
            if(vsjShoppingCart.getRegId()!=null){
                list.add("s.reg_id = #{regId}");
            }
            if(vsjShoppingCart.getRegId()!=null){
                list.add("s.platform_code = #{platformCode}");
            }
            return String.join(" and ",list);
        }

        public String deleteShoppingCart(@Param("cartId") Integer cartId, @Param("platformCode") Integer platformCode){
            String sql = new SQL(){{
                DELETE_FROM("vsj_shopping_cart");
                WHERE("cart_id = #{cartId} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }


    }

}
