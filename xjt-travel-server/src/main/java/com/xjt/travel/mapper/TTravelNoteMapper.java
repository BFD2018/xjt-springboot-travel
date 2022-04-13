package com.xjt.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjt.travel.domain.TTravelNote;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface TTravelNoteMapper extends BaseMapper<TTravelNote> {
    Map<String, Object> getTravelNoteDetail(String tn_id);

    List<Map<String, String>> getNoteByTypeLocation();

    Integer addTravelnote();
}
