package com.xjt.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjt.travel.domain.TRoute;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TRouteMapper extends BaseMapper<TRoute> {
    Map<String, String> getRouteDetailById(String id);

    List<Map> selectNumGroupByCategory();

    List<Map> selectNumGroupBySeller();
}
