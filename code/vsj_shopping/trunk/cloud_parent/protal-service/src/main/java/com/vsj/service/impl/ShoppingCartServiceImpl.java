package com.vsj.service.impl;

import com.vsj.common.model.BaseResponse;
import com.vsj.common.model.BaseResponseParam;
import com.vsj.dao.ShoppingCartDao;
import com.vsj.model.VsjShoppingCart;
import com.vsj.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//shoppingCartDao.deleteProductCateKey();

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Override
    public Object selectShoppingCartList(Integer regId, Integer platformCode) {
        if(regId == null && platformCode == null){
            return BaseResponse.fail("用户id或者code码不能为空");
        }
        List<VsjShoppingCart> vsjVsjShoppingCartList = shoppingCartDao.selectShoppingCartList(regId,platformCode);
        if(vsjVsjShoppingCartList !=null && vsjVsjShoppingCartList.size()>0){
            return BaseResponseParam.success(vsjVsjShoppingCartList);
        }
        return BaseResponse.fail();
    }

    @Override
    public Object insertShoppingCart(VsjShoppingCart vsjShoppingCart, Integer platformCode) {
        if(vsjShoppingCart == null){
            return BaseResponseParam.fail("参数不能为空");
        }
        vsjShoppingCart.setPlatformCode(platformCode);
        int result = shoppingCartDao.insertShoppingCart(vsjShoppingCart);
        if(result<=0){
            return BaseResponseParam.fail("查询失败");
        }
        return BaseResponseParam.success();
    }

    @Override
    public Object deleteShoppingCart(Integer cartId,Integer platformCode) {
        if(cartId==null){
            return BaseResponseParam.fail("购物车id不能为空");
        }
        int result = shoppingCartDao.deleteShoppingCart(cartId,platformCode);
        if(result<=0){
            return BaseResponseParam.fail("删除购物车失败");
        }
        return BaseResponseParam.success();
    }

}
