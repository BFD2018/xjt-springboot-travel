package com.xjt.travel.service;

import com.xjt.travel.utils.RespBean;

import java.util.HashMap;

/**
 * @author xiong
 * @ClassName TTravelNoteService.java
 * @createTime 2021/12/24
 * @Description TODO
 */
public interface TTravelNoteService {
    RespBean getTravelNoteByPage(String type, String title, String currentPage, String pageSize);

    RespBean getTravelNoteDetail(String tn_id);

    RespBean getCarousel();

    RespBean updateTravelNoteUp(Integer id);

    RespBean getNoteByTypeLocation();

    RespBean getAllTravelNoteNum();

    RespBean deleteTravelNoteById(String id);

    RespBean addTravelnote(HashMap<String, String> params);
}
