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
public class TRouteImg implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer routeimgId;

    private Integer routeId;
    private String pic;
    private Integer idx;        //图片索引
}
