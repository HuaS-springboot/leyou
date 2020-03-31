package com.vsj.dao;

import com.vsj.common.redis.client.RedisClient;
import com.vsj.common.redis.constant.RedisKeyConstant;
import com.vsj.mapper.ShoppingCartMapper;
import com.vsj.model.VsjShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HuaS
 * @Date :2019/7/30 13:52
 * @Describe:
 */
@Component
public class ShoppingCartDao {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private RedisClient redisClient;

    public void deleteProductCateKey(){
        redisClient.remove(RedisKeyConstant.SHOPPING_CART_KEY);
    }

    public List<VsjShoppingCart> selectShoppingCartList(Integer regId, Integer platformCode){
        return shoppingCartMapper.selectShoppingCartList(regId,platformCode);
    }

    public int insertShoppingCart(VsjShoppingCart vsjShoppingCart){
        return shoppingCartMapper.insertShoppingCart(vsjShoppingCart);
    }

    public int deleteShoppingCart(Integer cartId,Integer platformCode){
        return shoppingCartMapper.deleteShoppingCart(cartId,platformCode);
    }

}
