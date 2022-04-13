package com.xjt.travel.controller;

import com.xjt.travel.service.TRoleService;
import com.xjt.travel.utils.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/role")
public class TRoleController {
    @Autowired
    private TRoleService roleService;

    //获取所有用户及其权限
    @RequiresPermissions("system:user:*")
    @GetMapping("/all")
    public RespBean getAllRolePerms(){
        return roleService.getAllRolePerms();
    }

    @RequiresPermissions("system:user:delete")
    @GetMapping("/delete")
    public RespBean deleteRole(@RequestParam("id") String id){
        return roleService.deleteRoleById(id);
    }
}
