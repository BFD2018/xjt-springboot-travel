package com.xjt.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TTravelNote {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;
    private String coverImage;
    private String description;
    private String location;
    private String viewCount;
    private Integer up;
    private Boolean type;       //true-热门游记  false-最新发布
    private LocalDateTime createTime;
}
