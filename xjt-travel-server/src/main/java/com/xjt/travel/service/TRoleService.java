package com.xjt.travel.service;

import com.xjt.travel.domain.TUser;
import com.xjt.travel.utils.RespBean;

/**
 * @author xiong
 * @ClassName TRoleService.java
 * @createTime 2022/1/19
 * @Description TODO
 */
public interface TRoleService {
    RespBean getAllRolePerms();

    RespBean deleteRoleById(String id);

    int assignRoleByUserId(TUser tUser);
}
