package com.xjt.travel.controller;

import com.xjt.travel.service.TRouteService;
import com.xjt.travel.utils.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/route")
public class TRouteController {
    @Autowired
    private TRouteService tRouteService;

    @GetMapping("/getByPage")
    public RespBean getRouteByPage(@RequestParam(value = "currentPage", defaultValue = "1") String currentPage,
                                    @RequestParam(value = "pageSize", defaultValue = "6") String pageSize,
                                    @RequestParam(value = "category_id", defaultValue = "1") String category_id,
                                    @RequestParam(value = "route_name", required = false) String route_name
    ) {
        log.warn("currentPage===>" + currentPage);
        log.warn("pageSize===>" + pageSize);
        log.warn("category_id===>" + category_id);
        log.warn("route_name===>" + route_name);
        return tRouteService.getRouteListByPage(currentPage, pageSize, category_id, route_name);
    }

    @GetMapping("/getById")
    public RespBean getRouteById(@RequestParam(value = "id") String id
    ) {
        return tRouteService.getRouteById(id);
    }

    @GetMapping("/getImgsById")
    public RespBean getRouteImgsById(@RequestParam(value = "id") String id) {
        return tRouteService.getRouteImgsById(id);
    }

    @GetMapping("/allRouteBySellerId")
    public RespBean getAllRouteBySellerId(@RequestParam(value = "seller_id") String seller_id) {
        return tRouteService.getAllRouteBySellerId(seller_id);
    }

    /*后端：模糊搜索*/
    @GetMapping("/queryByConditions")
    public RespBean queryRoutesByConditions(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "flag", required = false) String flag,
            @RequestParam(value = "category_id", required = false) String category_id,
            @RequestParam(value = "lowel_price", required = false) String lowel_price,
            @RequestParam(value = "high_price", required = false) String high_price,
            @RequestParam(value = "currentPage", defaultValue = "1") String currentPage,
            @RequestParam(value = "pageSize", defaultValue = "6") String pageSize
    ) {
        log.warn("flag===>" + flag);
        return tRouteService.queryRoutesByConditions(title,flag,category_id,lowel_price,high_price,currentPage, pageSize);
    }

    @RequiresPermissions("system:route:delete")
    @GetMapping("/delete")
    public RespBean deleteRouteById(@RequestParam(value = "id") String id) {
        return tRouteService.deleteRouteById(id);
    }

    @RequiresPermissions("system:route:add")
    @PostMapping("/add")     //新增旅游路线
    public RespBean addRoute(@RequestBody HashMap<String, String> params) {
        System.out.println(params);
        return tRouteService.addRoute(params);
    }

    //改变状态
    @RequiresPermissions("system:route:delete")
    @GetMapping("/toggle/flag")
    public RespBean toggleRouteFlagById(@RequestParam(value = "id") String id) {
        return tRouteService.toggleRouteFlagById(id);
    }

    //路线 -- 词云图
    @GetMapping("/wordcloud")
    public RespBean routeWordCloud(){
        return tRouteService.generateRouteWordCloud();
    }

    //路线总数
    @GetMapping("/all/num")
    public RespBean getAllRouteNum(){
        return tRouteService.getAllRouteNum();
    }

    //根据category分类查找route
    @GetMapping("/getNumByCategory")
    public RespBean getNumByCategory(){
        return tRouteService.getNumByCategory();
    }

    //根据seller分类查找route
    @GetMapping("/getNumBySeller")
    public RespBean getNumBySeller(){
        return tRouteService.getNumBySeller();
    }
}
