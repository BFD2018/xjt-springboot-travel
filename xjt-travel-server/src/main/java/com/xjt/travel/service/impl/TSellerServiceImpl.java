package com.xjt.travel.service.impl;

import com.xjt.travel.domain.TSeller;
import com.xjt.travel.mapper.TSellerMapper;
import com.xjt.travel.service.TSellerService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TSellerServiceImpl implements TSellerService {
    @Autowired
    private TSellerMapper sellerMapper;

    @Override
    public RespBean getAllRouteById(String seller_id) {
        return null;
    }

    @Override
    public RespBean getById(String seller_id) {
        TSeller seller = sellerMapper.selectById(seller_id);

        if(ObjectUtils.isEmpty(seller)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",seller);
        }
    }

    @Override
    public RespBean getAllSeller() {
        List<TSeller> sellerList = sellerMapper.selectList(null);

        if(ObjectUtils.isEmpty(sellerList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",sellerList);
        }
    }

    @Override
    public RespBean deleteSellerById(String seller_id) {
        int i = sellerMapper.deleteById(seller_id);

        if(i<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean updateSellerById(TSeller seller) {
        int update = sellerMapper.updateById(seller);

        if(update<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }
}
