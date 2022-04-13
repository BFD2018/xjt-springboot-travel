package com.xjt.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjt.travel.domain.TTravelNote;
import com.xjt.travel.domain.TTraveller;
import com.xjt.travel.domain.TTravellerNote;
import com.xjt.travel.mapper.TTravelNoteMapper;
import com.xjt.travel.mapper.TTravellerMapper;
import com.xjt.travel.mapper.TTravellerNoteMapper;
import com.xjt.travel.service.TTravellerService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TTravellerServiceImpl implements TTravellerService {
    @Autowired
    private TTravellerMapper travellerMapper;

    @Autowired
    private TTravellerNoteMapper travellerNoteMapper;

    @Override
    public RespBean getRecommendTraveller(String limit) {
        QueryWrapper<TTraveller> wrapper = new QueryWrapper<>();
        //wrapper.last("limit "+limit);
        wrapper.last("order by RAND() limit "+limit);

        List<TTraveller> travellerList = travellerMapper.selectList(wrapper);
        if(ObjectUtils.isEmpty(travellerList)){
            return RespBean.warn("null");
        }else{
            return RespBean.success("ok",travellerList);
        }
    }

    @Override
    public RespBean getTravellerAuthorInfo(String id) {
        TTraveller traveller = travellerMapper.selectById(id);
        if(ObjectUtils.isEmpty(traveller)){
            return RespBean.warn("null");
        }else{
            return RespBean.success("ok",traveller);
        }
    }

    @Override
    public RespBean queryTravellerByConditions(String name, String currentPage, String pageSize) {
        Page<TTraveller> page = new Page<TTraveller>(Integer.parseInt(currentPage),Integer.parseInt(pageSize));
        QueryWrapper<TTraveller> wrapper = new QueryWrapper<>();

        if(StringUtils.hasText(name) && !ObjectUtils.isEmpty(name)){
            wrapper.eq("name",name);
        }
        travellerMapper.selectPage(page, wrapper);

        if(ObjectUtils.isEmpty(page)){
            return RespBean.warn("null");
        }else{
            return RespBean.success("ok",page);
        }
    }

    @Override
    public RespBean deleteTravellerById(String id) {
        //1、先删除作者的游记
        int delete1 = travellerNoteMapper.delete(new QueryWrapper<TTravellerNote>().eq("writer_id", id));
        //2、再删除作者
        int delete2 = travellerMapper.deleteById(id);

        if(delete1 < 0 || delete2 <0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean insertTraveller(TTraveller traveller) {
        int insert = travellerMapper.insert(traveller);

        if(insert<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean updateTravellerById(TTraveller traveller) {
        int ret = travellerMapper.updateById(traveller);

        if(ret<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }

    }
}
