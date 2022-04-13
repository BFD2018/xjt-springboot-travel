package com.xjt.travel.service;

import com.xjt.travel.utils.RespBean;

/**
 * @author xiong
 * @ClassName TTravellerNoteService.java
 * @createTime 2021/12/29
 * @Description TODO
 */
public interface TTravellerNoteService {
    RespBean getTravellerNoteByTime(String limit);

    RespBean getTravellerNoteByPage(String orderBy,String currentPage, String pageSize);

    RespBean getTravellerAuthorArticles(String id);

    RespBean getArticleAndAuthor(String id);

    RespBean queryArticlesByConditions(String title, String author_name, String currentPage, String pageSize);

    RespBean deleteArticleById(String id);

    RespBean getArticleGroupByTraveller(String num);
}
