package com.xjt.travel.service;

import com.xjt.travel.domain.TSeller;
import com.xjt.travel.utils.RespBean;

/**
 * @author xiong
 * @ClassName TSellerService.java
 * @createTime 2022/1/7
 * @Description TODO
 */
public interface TSellerService {
    RespBean getAllRouteById(String seller_id);

    RespBean getById(String seller_id);

    RespBean getAllSeller();

    RespBean deleteSellerById(String seller_id);

    RespBean updateSellerById(TSeller seller);
}
