package com.xjt.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userId;     //用户id

    private String username;        //用户名，账号
    private String password;        //密码
    private String nickname;        //密码
    private String avatar;      //头像
    private Date birthday;      //出生日期
    private Integer sex;            //1-男  0-女
    private String telephone;       //手机号
    private String email;       //邮箱
    private Boolean status;      //激活状态，Y代表激活，N代表未激活
    private String salt;        //盐

    //定义角色集合
    @TableField(exist = false)
    private List<TRole> rolesList;
}
