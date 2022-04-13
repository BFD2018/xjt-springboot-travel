package com.xjt.travel.controller;

import com.xjt.travel.service.TTravelNoteService;
import com.xjt.travel.utils.RespBean;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiresAuthentication
@RestController
@RequestMapping("/travelNote")
public class TTravelNoteController {
    @Autowired
    private TTravelNoteService travelNoteService;

    /*按type、title关键字、页码 》条件查询游记*/
    @RequiresPermissions("system:travelnote:*")
    @GetMapping("/getByTitleType")
    public RespBean getTravelNoteByPage(@RequestParam(value = "type",required = false) String type,
                                        @RequestParam(value = "title",required = false) String title,
                                        @RequestParam(value = "currentPage",defaultValue = "1") String currentPage,
                                        @RequestParam(value = "pageSize",defaultValue = "6") String pageSize){
        return travelNoteService.getTravelNoteByPage(type,title,currentPage,pageSize);
    }

    //总数
    @GetMapping("/all/num")
    public RespBean getAllTravelNoteNum(){
        return travelNoteService.getAllTravelNoteNum();
    }

    //根据type和location 查找游记数量（Line图）
    @RequiresPermissions("system:travelnote:get")
    @GetMapping("/byTypeLocation")
    public RespBean getNoteByTypeLocation(){
        return travelNoteService.getNoteByTypeLocation();
    }

    @GetMapping("/detail")
    public RespBean getTravelNoteDetail(@RequestParam("tn_id") String tn_id){
        return travelNoteService.getTravelNoteDetail(tn_id);
    }

    @RequiresPermissions("system:travelnote:delete")
    @GetMapping("/delete")
    public RespBean deleteTravelNoteById(@RequestParam("id") String id){
        return travelNoteService.deleteTravelNoteById(id);
    }

    @GetMapping("/getByUpDesc")
    public RespBean getCarousel(){
        return travelNoteService.getCarousel();
    }

    @GetMapping("/up/add")
    public RespBean updateTravelNoteUp(@RequestParam(value = "id") Integer id){
        return travelNoteService.updateTravelNoteUp(id);
    }

    @RequiresPermissions("system:travelnote:add")
    @PostMapping("add")
    public RespBean addTravelnote(@RequestBody HashMap<String,String> params){
        return travelNoteService.addTravelnote(params);
    }
}
