package com.xjt.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TSeller implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer sellerId;           //商家id

    private String name;            //商家名称
    private String fullname;        //商家电话
    private String logo;        //商家地址
}
