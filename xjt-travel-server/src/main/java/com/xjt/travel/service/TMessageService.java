package com.xjt.travel.service;

import com.xjt.travel.utils.RespBean;

import java.util.HashMap;

/**
 * @author xiong
 * @ClassName TMessageService.java
 * @createTime 2022/1/10
 * @Description TODO
 */
public interface TMessageService {
    RespBean saveMessage(HashMap<String, String> params);

    RespBean deleteMessageById(Integer id);

    RespBean getAllMessage();

    RespBean goodById(Integer id);
}
