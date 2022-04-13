package com.xjt.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TRole {
    @TableId(type = IdType.AUTO)
    private Integer roleId;
    private String name;
    private String nameZh;

    //定义权限的集合
    private List<TPerm> permsList;
}
