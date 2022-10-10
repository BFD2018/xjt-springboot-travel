package com.xjt.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjt.travel.domain.TRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TRoleMapper extends BaseMapper<TRole> {
    List<Map<String, String>> queryAllRolePerms();

    void assignUserRole(@Param("userId") Integer userId, @Param("roleName") String roleName);
}
