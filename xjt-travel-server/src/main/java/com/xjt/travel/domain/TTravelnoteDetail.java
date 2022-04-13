package com.xjt.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TTravelnoteDetail implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;     //用户id

    private Integer tnId;

    private String publiserAvatar;
    private String publiserName;
    private String publishTime;
    private String startTime;
    private String day;
    private String cost;
    private String content;
    private Date createTime;

}
