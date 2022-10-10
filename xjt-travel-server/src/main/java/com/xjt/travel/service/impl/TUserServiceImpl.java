package com.xjt.travel.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjt.travel.domain.TPerm;
import com.xjt.travel.domain.TRole;
import com.xjt.travel.domain.TUser;
import com.xjt.travel.mapper.TUserMapper;
import com.xjt.travel.service.TRoleService;
import com.xjt.travel.service.TUserService;
import com.xjt.travel.utils.RespBean;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.management.relation.Role;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TUserServiceImpl implements TUserService {
    @Autowired
    private TUserMapper tUserMapper;

    @Autowired
    private TRoleService tRoleService;

    @Transactional
    @Override
    public RespBean addUser(String username, String password) {
        synchronized (this){
            //普通用户
            TUser tUser = new TUser().setUsername(username);
            int millisecond = DateUtil.millisecond(new Date());
            tUser.setUserId(millisecond);
            String salt = String.valueOf(RandomUtil.randomInt(9999));
            Md5Hash md5Hash = new Md5Hash(password, salt, 1024);
            tUser.setPassword(md5Hash.toHex());
            tUser.setSalt(salt);

            int insert = tUserMapper.insert(tUser);
            //分配角色
            tUser.setRolesList(Arrays.asList(new TRole().setName("common")));
            tRoleService.assignRoleByUserId(tUser);

            if(insert < 0){
                return RespBean.error("注册用户失败");
            }else{
                return RespBean.success("注册用户成功",tUser);
            }
        }
    }

    @Override
    public TUser findRolesByUsername(String principal) {
        TUser tUser = tUserMapper.getRolesByUsername(principal);
        return tUser;
    }

    @Override
    public List<TPerm> findPermsListByRoleId(Integer roleId) {
        return tUserMapper.findPermsListByRoleId(roleId);
    }

    @Override
    public TUser findByUsername(String principal) {
        return tUserMapper.selectOne(new QueryWrapper<TUser>().eq("username",principal));
    }

    @Override
    public RespBean getAllUser() {
        List<TUser> userList = tUserMapper.selectList(null);

        if(ObjectUtils.isEmpty(userList)){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok",userList);
        }
    }

    @Override
    public RespBean updateUserStatusById(String id) {
        TUser tUser = tUserMapper.selectById(id);
        if(tUser.getStatus() == false){
            tUser.setStatus(true);
        }else{
            tUser.setStatus(false);
        }
        int update = tUserMapper.updateById(tUser);
        if(update<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean insertOne(TUser tUser) {
        String salt = String.valueOf(RandomUtil.randomInt(9999));
        Md5Hash md5Hash = new Md5Hash(tUser.getPassword(), salt, 1024);
        tUser.setPassword(md5Hash.toHex());
        tUser.setSalt(salt);
        int insert = tUserMapper.insert(tUser);

        if(insert<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean deleteUserById(String id) {
        int dd = tUserMapper.deleteById(id);
        if(dd<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean updateUser(TUser tUser) {
        int update = tUserMapper.updateById(tUser);
        if(update<0){
            return RespBean.error("error");
        }else{
            return RespBean.success("ok");
        }
    }

    @Override
    public RespBean queryByCondition(String current, String pageSize, String id, String username, String nickname, String sex, String status) {
        Page<TUser> page = new Page<TUser>(Integer.valueOf(current), Integer.valueOf(pageSize));
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        if(StringUtils.hasText(username)  && !ObjectUtils.isEmpty(username)){
            wrapper.like("username",username);
        }
        if(StringUtils.hasText(nickname) && !ObjectUtils.isEmpty(nickname)){
            wrapper.like("nickname",nickname);
        }
        if(StringUtils.hasText(id) && !ObjectUtils.isEmpty(id) ){
            wrapper.eq("id",id);
        }
        if(StringUtils.hasText(sex) && !ObjectUtils.isEmpty(sex)){
            wrapper.eq("sex",sex);
        }
        if(StringUtils.hasText(status) && !ObjectUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }

        tUserMapper.selectPage(page,wrapper);
        List<TUser> records = page.getRecords();

        if(ObjectUtils.isEmpty(records)){
            return RespBean.warn("没找到符合条件的User");
        }else{
            return RespBean.success("ok",page);
        }
    }

    @Override
    public Set<String> getPermissionsByUsername(String principal) {
        return tUserMapper.getAllPermissionsByUsername(principal);
    }
}
