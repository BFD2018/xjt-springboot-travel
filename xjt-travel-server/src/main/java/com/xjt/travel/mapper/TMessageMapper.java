package com.xjt.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjt.travel.domain.TMessage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TMessageMapper extends BaseMapper<TMessage> {
    List<Map<String, Object>> selectLevelOneMessage(String parent_id);

    List<Map<String, Object>> selectLevelOneReplys(String parent_id);
}
