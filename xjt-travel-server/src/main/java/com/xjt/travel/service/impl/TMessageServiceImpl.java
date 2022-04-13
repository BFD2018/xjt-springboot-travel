package com.xjt.travel.service.impl;

import com.xjt.travel.domain.TMessage;
import com.xjt.travel.mapper.TMessageMapper;
import com.xjt.travel.service.TMessageService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TMessageServiceImpl implements TMessageService {
    @Autowired
    private TMessageMapper messageMapper;

    @Override
    public RespBean saveMessage(HashMap<String, String> params) {
        String user_id = params.get("user_id");
        String content = params.get("content");
        String parent_id = params.get("parent_id");
        TMessage tMessage = new TMessage().setUserId(Integer.valueOf(user_id))
                .setContent(content).setParentId(Integer.valueOf(parent_id));

        int insert = messageMapper.insert(tMessage);
        if(insert<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean deleteMessageById(Integer id) {
        int i = messageMapper.deleteById(id);

        if(i<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean getAllMessage() {
        //1、查找所有父节点-给站长的直接留言（一级）
        List<Map<String, Object>> levelOneMessageList = messageMapper.selectLevelOneMessage("-1");

        for (Map<String, Object> levelOneMessage : levelOneMessageList) {
            //一级评论的id
            String id = String.valueOf(levelOneMessage.get("id"));

            //2、站长给留言者的回复（二级）
            List<Map<String, Object>> levelOneReplyList = messageMapper.selectLevelOneReplys(id);

            levelOneMessage.put("replyMessages",levelOneReplyList);
        }

        return RespBean.success("ok",levelOneMessageList);
    }

    @Override
    public RespBean goodById(Integer id) {
        TMessage tMessage = messageMapper.selectById(id);
        tMessage.setCounts(tMessage.getCounts()+1);
        int i = messageMapper.updateById(tMessage);

        if(i<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }
}
