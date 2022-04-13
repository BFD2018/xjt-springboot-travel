package com.xjt.travel.controller;

import com.xjt.travel.service.TMessageService;
import com.xjt.travel.utils.RespBean;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author xiong
 * @Description //留言
 * @Date 2022/1/10
 * @param
 * @return
 **/
@RestController
@RequestMapping("/message")
public class TMessageController {
    @Autowired
    private TMessageService messageService;

    @PostMapping("/save")
    public RespBean saveMessage(@RequestBody HashMap<String,String> params) {
        System.out.println("params===================>");
        System.out.println(params);

        return messageService.saveMessage(params);
    }

    @RequiresPermissions("system:*:*")
    @GetMapping("/delete")
    public RespBean delete(@RequestParam("id") Integer id) {
        return messageService.deleteMessageById(id);
    }

    /*对留言点赞*/
    @GetMapping("/goodById")
    public RespBean goodById(@RequestParam("id") Integer id) {
        return messageService.goodById(id);
    }

    /*给站长的所有留言*/
    @GetMapping("/all")
    public RespBean getAllMessage() {
        return messageService.getAllMessage();
    }
}
