package com.xjt.travel.controller;

import com.xjt.travel.domain.TCart;
import com.xjt.travel.service.TCartService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/cart")
public class TCartController {
    @Autowired
    private TCartService cartService;

    /*加入购物车*/
    @PostMapping("/add")
    private RespBean addRouteToCart(@RequestBody HashMap<String,String> params){
        Integer route_id = Integer.valueOf(params.get("route_id"));
        Integer user_id = Integer.valueOf(params.get("user_id"));
        Integer num = Integer.valueOf(params.containsKey("num")?params.get("num"):"1");
        Integer checked = Integer.valueOf(params.containsKey("checked")?params.get("checked"):"1");

        //先判断商品是否已加入购物车
        TCart cart = cartService.isExist(route_id,user_id);
        //1、如果已加入（数量加1）
        if(!ObjectUtils.isEmpty(cart)){
            Integer goodsNum = cart.getGoodsNum();
            Integer cart_id = cart.getCartId();
            return cartService.updateCartGoodsNumById(cart_id,goodsNum);
        }else{
            //2、没有加入（插入一条记录）
            return cartService.addRouteToCart(route_id,user_id,num,checked);
        }
    }

    //查看购物车商品
    @GetMapping("/all/byUserid")
    private RespBean getCartAllRouteByUserId(
            @RequestParam(value = "user_id") Integer user_id
    ){
        return cartService.getCartAllRouteByUserId(user_id);
    }

    @GetMapping("/update/num/byCartid")
    private RespBean updateCartGoodsNumById(
            @RequestParam(value = "cart_id") Integer cart_id,
            @RequestParam(value = "num") Integer num
    ){
        return cartService.updateCartGoodsNumById(cart_id,num);
    }

    @GetMapping("/delete/byCartid")
    private RespBean deleteGoodsFromCartByCartId(
            @RequestParam(value = "cart_id") Integer cart_id
    ){
        return cartService.deleteGoodsFromCartByCartId(cart_id);
    }

    //购物车数量
    @GetMapping("/all/num")
    private RespBean getAllCartNum(){
        return cartService.getAllCartNum();
    }
}
