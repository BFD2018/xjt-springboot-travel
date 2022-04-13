package com.xjt.travel.service;

import com.xjt.travel.utils.RespBean;

import java.util.HashMap;

public interface TRouteService {
    RespBean getRouteListByPage(String currentPage, String pageSize, String category_id, String route_name);

    RespBean getRouteById(String id);

    RespBean getRouteImgsById(String id);

    RespBean getAllRouteBySellerId(String id);

    RespBean queryRoutesByConditions(String title, String flag, String category_id, String lowel_price, String high_price, String currentPage, String pageSize);

    RespBean deleteRouteById(String id);

    RespBean toggleRouteFlagById(String id);

    RespBean generateRouteWordCloud();


    RespBean getAllRouteNum();

    RespBean addRoute(HashMap<String, String> params);

    RespBean getNumByCategory();

    RespBean getNumBySeller();
}
