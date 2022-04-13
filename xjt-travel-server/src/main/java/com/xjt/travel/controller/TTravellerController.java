package com.xjt.travel.controller;

import com.xjt.travel.domain.TTraveller;
import com.xjt.travel.service.TTravellerService;
import com.xjt.travel.utils.RespBean;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiresAuthentication
@RestController
@RequestMapping("/traveller")
public class TTravellerController {
    @Autowired
    private TTravellerService travellerService;

    //获取推荐作者
    @GetMapping("/recommend")
    public RespBean getRecommendTraveller(@RequestParam(value = "limit",defaultValue = "5",required = false) String limit){
        return travellerService.getRecommendTraveller(limit);
    }

    //通过id获取用户信息
    @GetMapping("/author")
    public RespBean getTravellerAuthorInfo(@RequestParam(value = "id") String id){
        return travellerService.getTravellerAuthorInfo(id);
    }

/*后台请求*/
    //条件查询
    @RequiresPermissions("system:traveller:get")
    @GetMapping("/queryByConditions")
    public RespBean queryTravellerByConditions(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "currentPage",defaultValue = "1") String currentPage,
            @RequestParam(value = "pageSize",defaultValue = "6") String pageSize
    ){
        return travellerService.queryTravellerByConditions(name,currentPage,pageSize);
    }

    /*根据id删除旅行家及其所有游记*/
    @RequiresPermissions("system:traveller:delete")
    @GetMapping("/delete")
    public RespBean deleteTravellerById(
            @RequestParam(value = "id") String id
    ){
        return travellerService.deleteTravellerById(id);
    }

    //修改
    @RequiresPermissions("system:traveller:update")
    @PostMapping("/update")
    public RespBean updateTravellerById(@RequestBody TTraveller traveller){
        System.out.println(traveller);
        return travellerService.updateTravellerById(traveller);
    }

    //新增
    @RequiresPermissions("system:traveller:add")
    @PostMapping("/insert")
    public RespBean insertTraveller(@RequestBody TTraveller traveller){
        System.out.println(traveller);
        return travellerService.insertTraveller(traveller);
    }
}
