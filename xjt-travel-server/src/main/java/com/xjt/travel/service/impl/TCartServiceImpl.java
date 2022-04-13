package com.xjt.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xjt.travel.domain.TCart;
import com.xjt.travel.mapper.TCartMapper;
import com.xjt.travel.service.TCartService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.repository.config.QueryCreatorType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class TCartServiceImpl implements TCartService {
    @Autowired
    private TCartMapper cartMapper;

    @Override
    public RespBean addRouteToCart(Integer route_id, Integer user_id, Integer num, Integer checked) {
        TCart cart = new TCart().setGoodsId(route_id).setUserId(user_id).setGoodsNum(num).setChecked(checked);
        int insert = cartMapper.insert(cart);
        if(insert<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok","商品成功加入购物车！");
        }
    }

    @Override
    public RespBean getCartAllRouteByUserId(Integer user_id) {
        List<Map<String,String>> cartList = cartMapper.selectAllByUserId(user_id);

        if(ObjectUtils.isEmpty(cartList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",cartList);
        }
    }

    @Override
    public RespBean updateCartGoodsNumById(Integer cart_id, Integer num) {
        TCart cart = new TCart().setCartId(cart_id).setGoodsNum(num);
        int update = cartMapper.updateById(cart);

        if(update<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok","购物车商品数量已更新！");
        }
    }

    @Override
    public RespBean deleteGoodsFromCartByCartId(Integer cart_id) {
        int i = cartMapper.deleteById(cart_id);

        if(i<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok","商品已从购物车删除！");
        }
    }

    @Override
    public TCart isExist(Integer route_id, Integer user_id) {
        QueryWrapper<TCart> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",route_id);
        wrapper.eq("user_id",user_id);

        TCart tCart = cartMapper.selectOne(wrapper);

        return tCart;

    }

    @Override
    public RespBean getAllCartNum() {
        Integer count = cartMapper.selectCount(null);
        if(count<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",count);
        }
    }
}
