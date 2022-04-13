package com.xjt.travel.controller;

import com.xjt.travel.service.TTravellerNoteService;
import com.xjt.travel.utils.RespBean;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiresAuthentication
@RestController
@RequestMapping("/traveller/note")
public class TTravellerNoteController {
    @Autowired
    private TTravellerNoteService travellerNoteService;

    //通过id获取旅行作品 及作者信息
    @GetMapping("/article/byId")
    public RespBean getArticleAndAuthor(@RequestParam(value = "id") String id){
        return travellerNoteService.getArticleAndAuthor(id);
    }

    @GetMapping("/getByTime")
    public RespBean getTravellerNoteByTime(@RequestParam(value = "limit",defaultValue = "6") String limit){
        return travellerNoteService.getTravellerNoteByTime(limit);
    }

    @GetMapping("/getByPage")
    public RespBean getTravellerNoteByPage(
            @RequestParam(value = "orderBy",defaultValue = "publish_time") String orderBy,
            @RequestParam(value = "currentPage",defaultValue = "1") String currentPage,
            @RequestParam(value = "pageSize",defaultValue = "6") String pageSize){
        return travellerNoteService.getTravellerNoteByPage(orderBy,currentPage,pageSize);
    }

    //通过writer_id获取旅行家的所有作品
    @GetMapping("/articles/byWriterId")
    public RespBean getTravellerAuthorAllArticle(@RequestParam(value = "id") String id){
        return travellerNoteService.getTravellerAuthorArticles(id);
    }

    //根据旅行家id分组，降序
    @GetMapping("/groupByTraveller")
    public RespBean getArticleGroupByTraveller(@RequestParam(value = "num",defaultValue = "10") String num){
        return travellerNoteService.getArticleGroupByTraveller(num);
    }

/*后台请求*/
    //条件分页查询
    @RequiresPermissions("system:travelnote:get")
    @GetMapping("/queryByConditions")
    public RespBean queryArticlesByConditions(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "author_name",required = false) String author_name,
            @RequestParam(value = "currentPage",defaultValue = "1") String currentPage,
            @RequestParam(value = "pageSize",defaultValue = "6") String pageSize){
        return travellerNoteService.queryArticlesByConditions(title,author_name,currentPage,pageSize);
    }

    //通过id删除
    @RequiresPermissions("system:travelnote:delete")
    @GetMapping("/delete")
    public RespBean deleteArticleById(@RequestParam(value = "id") String id){
        return travellerNoteService.deleteArticleById(id);
    }
}
