package com.xjt.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TRoute implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;        //线路id

    private String title;
    private String routePhoto;      //存图片地址 以逗号分隔
    private Integer price;

    private String introduction;
    private Boolean flag;       //是否上架，0代表没有上架，1代表是上架

    private Integer counts;     //收藏数量

    private String location;        //目的地名，北京，上海
    private Integer categoryId;     //所属分类，必输
    private Integer sellerId;       //所属商家

    private Timestamp publishTime;   //上架时间

    @TableField(exist = false)
    private List<TRouteImg> routeImgList;    //商品图文详情图片列表
}
