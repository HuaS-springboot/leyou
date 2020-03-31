package com.vsj.service;

import com.vsj.model.VsjShoppingCart;

public interface ShoppingCartService {
    Object selectShoppingCartList(Integer regId, Integer platformCode);

    Object insertShoppingCart(VsjShoppingCart vsjShoppingCart, Integer platformCode);

    Object deleteShoppingCart(Integer cartId, Integer platformCode);

}
