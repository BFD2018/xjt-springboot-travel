package com.xjt.travel.service.impl;

import com.xjt.travel.mapper.TRoleMapper;
import com.xjt.travel.service.TRoleService;
import com.xjt.travel.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class TRoleServiceImpl implements TRoleService {
    @Autowired
    private TRoleMapper roleMapper;

    @Override
    public RespBean getAllRolePerms() {
        List<Map<String,String>> mapList = roleMapper.queryAllRolePerms();
        if(ObjectUtils.isEmpty(mapList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",mapList);
        }
    }

    @Override
    public RespBean deleteRoleById(String id) {
        int i = roleMapper.deleteById(id);
        if(i<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }
}
