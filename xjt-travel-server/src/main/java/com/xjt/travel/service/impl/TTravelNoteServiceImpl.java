package com.xjt.travel.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjt.travel.domain.TTravelNote;
import com.xjt.travel.domain.TTravelnoteDetail;
import com.xjt.travel.mapper.TTravelNoteDetailMapper;
import com.xjt.travel.mapper.TTravelNoteMapper;
import com.xjt.travel.service.TTravelNoteService;
import com.xjt.travel.utils.MyConstant;
import com.xjt.travel.utils.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Slf4j
@Service
public class TTravelNoteServiceImpl implements TTravelNoteService {
    @Autowired
    private TTravelNoteMapper travelNoteMapper;

    @Autowired
    private TTravelNoteDetailMapper travelNoteDetailMapper;

    @Override
    public RespBean getTravelNoteByPage(String type, String title, String currentPage, String pageSize) {
        Page<TTravelNote> notePage = new Page<TTravelNote>(Integer.parseInt(currentPage),Integer.parseInt(pageSize));
        QueryWrapper<TTravelNote> wrapper = new QueryWrapper<>();

        if(StringUtils.hasText(type) && !ObjectUtils.isEmpty(type)){
            wrapper.eq("type",type);
        }

        if(StringUtils.hasText(title) && !ObjectUtils.isEmpty(title) ){
            wrapper.like("title",title);
        }

        travelNoteMapper.selectPage(notePage, wrapper);
        if(ObjectUtils.isEmpty(notePage)){
            return RespBean.warn("null");
        }else{
            return RespBean.success("ok",notePage);
        }
    }

    @Override
    public RespBean getTravelNoteDetail(String tn_id) {
        Map<String,Object> res =  travelNoteMapper.getTravelNoteDetail(tn_id);

        String content_url = (String)res.get("content");
        FileReader fileReader = new FileReader(MyConstant.ProjectPath + content_url);
        String s = fileReader.readString();
        try {
            res.put("content",s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RespBean.success("ok",res);
    }

    @Override
    public RespBean getCarousel() {
        QueryWrapper<TTravelNote> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("up");
        wrapper.last("limit 6");
        List<TTravelNote> travelNoteList = travelNoteMapper.selectList(wrapper);
        if(ObjectUtils.isEmpty(travelNoteList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",travelNoteList);
        }
    }

    @Override
    public RespBean updateTravelNoteUp(Integer id) {
        TTravelNote note = travelNoteMapper.selectById(id);
        note.setUp(note.getUp() + 1);

        int update = travelNoteMapper.updateById(note);
        if(update<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean getNoteByTypeLocation() {
        List<Map<String,String>> list = travelNoteMapper.getNoteByTypeLocation();
        if(ObjectUtils.isEmpty(list)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",list);
        }
    }

    @Override
    public RespBean getAllTravelNoteNum() {
        Integer count = travelNoteMapper.selectCount(null);
        if(ObjectUtils.isEmpty(count)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",count);
        }
    }

    @Override
    public RespBean deleteTravelNoteById(String id) {
        int i = travelNoteMapper.deleteById(id);
        if(i<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean addTravelnote(HashMap<String, String> params) {
        System.out.println(params);
        String title = params.get("title");
        String description = params.get("description");
        String coverImage = params.get("coverImage");
        String location = params.get("location");
        Boolean type = params.get("type")=="true"?true:false;

        String publiser_name = params.get("publiser_name");
        String publiser_avatar = params.get("publiser_avatar");
        String publish_time = params.get("publish_time");
        String start_time = params.get("start_time");
        String day = params.get("day");
        String cost = params.get("cost");
        String content = params.get("content");


        TTravelNote travelNote = new TTravelNote().setTitle(title).setCoverImage(coverImage).setDescription(description).setLocation(location).setType(type);
        int insert1 = travelNoteMapper.insert(travelNote);
        TTravelNote tTravelNote = travelNoteMapper.selectOne(new QueryWrapper<TTravelNote>().eq("title", title));

        // 根据日期动态的生成目录
        String uploadPath = null;
        String relativePath = "";
        try {
            uploadPath = ResourceUtils.getURL("travelFiles").getPath();
            File dateDirPath = new File(uploadPath);
            if (!dateDirPath.exists()) {
                dateDirPath.mkdirs();
            }
            relativePath = "files/" + DateUtil.today() + "/" +  tTravelNote.getId() +".html";
            log.warn("上传文件路径："+relativePath);
            FileWriter writer = new FileWriter(uploadPath + relativePath);
            writer.write(content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TTravelnoteDetail travelnoteDetail = new TTravelnoteDetail().setTnId(tTravelNote.getId()).setPubliserName(publiser_name)
                .setPubliserAvatar(publiser_avatar).setPublishTime(publish_time)
                .setStartTime(start_time).setDay(day).setCost(cost).setContent(relativePath);
        int insert2 = travelNoteDetailMapper.insert(travelnoteDetail);

        if(insert1<0 || insert2<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",tTravelNote.getId());
        }
    }
}
