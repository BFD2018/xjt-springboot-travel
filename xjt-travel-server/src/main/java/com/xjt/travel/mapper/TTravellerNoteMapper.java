package com.xjt.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjt.travel.domain.TTravellerNote;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TTravellerNoteMapper extends BaseMapper<TTravellerNote> {
    List<Map<String, String>> getTravellerNoteByPage(String orderBy);

    Map<String, String> getArticleAndAuthorInfo(String id);

    List<Map<String, String>> getArticlesByConditions(String title, String author_name);

    List<Map<String, String>> getArticleNumGroupByTraveller(String num);
}
