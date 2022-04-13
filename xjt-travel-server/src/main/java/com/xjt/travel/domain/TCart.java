package com.xjt.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TCart {
    @TableId(type = IdType.AUTO)
    private Integer cartId;

    private Integer userId;
    private Integer checked;
    private Integer goodsId;
    private Integer goodsNum;

    private Timestamp createTime;
    private Timestamp updateTime;
}
