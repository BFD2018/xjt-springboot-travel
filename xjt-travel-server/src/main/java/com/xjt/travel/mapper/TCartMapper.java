package com.xjt.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjt.travel.domain.TCart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TCartMapper extends BaseMapper<TCart> {
    List<Map<String, String>> selectAllByUserId(Integer user_id);
}
