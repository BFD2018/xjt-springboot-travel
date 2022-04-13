package com.xjt.travel.service;

import com.xjt.travel.domain.TCart;
import com.xjt.travel.utils.RespBean;

/**
 * @author xiong
 * @ClassName TCartService.java
 * @createTime 2022/1/7
 * @Description TODO
 */
public interface TCartService {
    RespBean addRouteToCart(Integer route_id, Integer user_id, Integer num, Integer checked);

    RespBean getCartAllRouteByUserId(Integer user_id);

    RespBean updateCartGoodsNumById(Integer cart_id, Integer num);

    RespBean deleteGoodsFromCartByCartId(Integer cart_id);

    TCart isExist(Integer route_id, Integer user_id);

    RespBean getAllCartNum();

}
