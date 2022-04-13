package com.xjt.travel.service;

import com.xjt.travel.domain.TPerm;
import com.xjt.travel.domain.TUser;
import com.xjt.travel.utils.RespBean;

import java.util.List;
import java.util.Set;

/**
 * @author xiong
 * @ClassName TUserService.java
 * @createTime 2021/12/20
 * @Description TODO
 */
public interface TUserService {
    RespBean addUser(String username, String password);

    TUser findRolesByUsername(String principal);

    List<TPerm> findPermsListByRoleId(Integer roleId);

    TUser findByUsername(String principal);

    RespBean getAllUser();

    RespBean updateUserStatusById(String id);

    RespBean insertOne(TUser tUser);

    RespBean deleteUserById(String id);

    RespBean updateUser(TUser tUser);

    RespBean queryByCondition(String current, String pageSize, String id, String username, String nickname, String sex, String status);

    Set<String> getPermissionsByUsername(String principal);
}
