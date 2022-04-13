package com.xjt.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TTravellerNote {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;
    private String thumbnail;
    private String description;
    //阅读数
    private Integer view;
    //作者id
    private Integer writerId;
    //文章详情（数据库存储的是html文件的路径）
    private String detailContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;
}
