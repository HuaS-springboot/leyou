package com.vsj.controller;


import com.vsj.common.BaseController;
import com.vsj.model.VsjShoppingCart;
import com.vsj.service.ShoppingCartService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/shoppingCart",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin
public class ShoppingCartController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShoppingCartService shoppingCartService;
/**
 * @Description:查看购物车
 * @Author HuaS
 * @Date   2019/8/23 11:25
 * @Param
 * getRegisterId:用户id
 * getPlatformCode：平台code码
 * @Return      
 * @Exception   
 *
 */
    @ApiOperation(value = "查看购物车", notes = "查看购物车", httpMethod = "POST")
    @RequestMapping("selectShoppingCartList")
    public Object selectShoppingCartList(){
        logger.info("查询购物车selectShoppingCartList入参:");
        Object obj = shoppingCartService.selectShoppingCartList(getRegisterId(),getPlatformCode());
        logger.info("查询购物车selectShoppingCartList出参:"+obj);
        return obj;
    }

    /**
     * @Description:添加购物车
     * @Author HuaS
     * @Date   2019/8/23 17:28
     * @Param  [VsjShoppingCart]
     * cartId:购物车id
     * regId：用户id
     * productId：产品id
     * productSpecsId：商品规格id
     * number：产品数量
     * isProductExists：商品是否有效
     * createdTime：创建时间
     * platformCode：平台code码
     * @Return
     * @Exception
     *
     */
    @ApiOperation(value = "添加购物车", notes = "添加购物车", httpMethod = "POST")
    @PostMapping("insertShoppingCart")
    public Object insertShoppingCart(@RequestBody VsjShoppingCart vsjShoppingCart){
        logger.info("添加购物车selectShoppingCartList入参:"+ vsjShoppingCart);
        Object obj = shoppingCartService.insertShoppingCart(vsjShoppingCart,getPlatformCode());
        logger.info("添加购物车selectShoppingCartList出参:"+obj);
        return obj;
    }

    /**
     * @Description:删除购物车
     * @Author HuaS
     * @Date   2019/8/23 17:34
     * @Param
     * cartId:购物车id
     * platformCode：平台code码
     * @Return  java.lang.Object
     * @Exception
     *
     */
    @ApiOperation(value = "删除购物车", notes = "删除购物车", httpMethod = "POST")
    @PostMapping("deleteShoppingCart")
    public Object deleteShoppingCart(@RequestBody VsjShoppingCart vsjShoppingCart){
        logger.info("删除购物车selectShoppingCartList入参:"+vsjShoppingCart);
        Object obj = shoppingCartService.deleteShoppingCart(vsjShoppingCart.getCartId(),getPlatformCode());
        logger.info("删除购物车selectShoppingCartList出参:"+obj);
        return obj;
    }

}
