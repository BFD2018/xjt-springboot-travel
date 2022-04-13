package com.xjt.travel.service.impl;

import cn.hutool.core.io.file.FileReader;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjt.travel.domain.TTravellerNote;
import com.xjt.travel.mapper.TTravellerNoteMapper;
import com.xjt.travel.service.TTravellerNoteService;
import com.xjt.travel.utils.MyConstant;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class TTravellerNoteServiceImpl implements TTravellerNoteService {
    @Autowired
    private TTravellerNoteMapper travellerNoteMapper;

    @Override
    public RespBean getTravellerNoteByTime(String limit) {
        QueryWrapper<TTravellerNote> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("publish_time","view");
        wrapper.last("limit "+limit);
        List<TTravellerNote> noteList = travellerNoteMapper.selectList(wrapper);
        if(ObjectUtils.isEmpty(noteList)){
            return RespBean.warn("null");
        }else{
            return RespBean.success("ok",noteList);
        }
    }

    @Override
    public RespBean getTravellerNoteByPage(String orderBy,String currentPage, String pageSize) {
        PageHelper.startPage(Integer.valueOf(currentPage),Integer.valueOf(pageSize));
        List<Map<String,String>> list = travellerNoteMapper.getTravellerNoteByPage(orderBy);

        if(ObjectUtils.isEmpty(list)){
            return RespBean.error("error");
        }else{
            PageInfo<Map<String,String>> pageInfo = new PageInfo<Map<String,String>>(list);

            return RespBean.success("ok",pageInfo);
        }
    }

    @Override
    public RespBean getTravellerAuthorArticles(String id) {
        List<TTravellerNote> articleList = travellerNoteMapper.selectList(new QueryWrapper<TTravellerNote>().eq("writer_id", id));

        if(ObjectUtils.isEmpty(articleList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",articleList);
        }
    }

    @Override
    public RespBean getArticleAndAuthor(String id) {
        Map<String,String> res = travellerNoteMapper.getArticleAndAuthorInfo(id);
        String content_url = (String)res.get("detail_content");
        FileReader fileReader = new FileReader(MyConstant.ProjectPath + content_url);
        String s = fileReader.readString();
        res.put("content",s);

        if(ObjectUtils.isEmpty(res)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",res);
        }
    }

    @Override
    public RespBean queryArticlesByConditions(String title, String author_name, String currentPage, String pageSize) {
        PageHelper.startPage(Integer.valueOf(currentPage),Integer.valueOf(pageSize));
        List<Map<String,String>> articleList = travellerNoteMapper.getArticlesByConditions(title,author_name);

        PageInfo<Map<String,String>> pageInfo = new PageInfo<Map<String,String>>(articleList);

        return RespBean.success("ok",pageInfo);
    }

    @Override
    public RespBean deleteArticleById(String id) {
        int i = travellerNoteMapper.deleteById(id);
        if(i<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean getArticleGroupByTraveller(String num) {
        List<Map<String,String>> list = travellerNoteMapper.getArticleNumGroupByTraveller(num);
        if(ObjectUtils.isEmpty(list)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",list);
        }
    }
}
