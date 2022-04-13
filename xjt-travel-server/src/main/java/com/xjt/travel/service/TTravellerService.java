package com.xjt.travel.service;

import com.xjt.travel.domain.TTraveller;
import com.xjt.travel.utils.RespBean;

/**
 * @author xiong
 * @ClassName TTravellerService.java
 * @createTime 2021/12/29
 * @Description TODO
 */
public interface TTravellerService {
    RespBean getRecommendTraveller(String limit);

    RespBean getTravellerAuthorInfo(String id);

    RespBean queryTravellerByConditions(String name, String currentPage, String pageSize);

    RespBean deleteTravellerById(String id);

    RespBean insertTraveller(TTraveller traveller);

    RespBean updateTravellerById(TTraveller traveller);
}
