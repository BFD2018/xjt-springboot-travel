# 星光旅游网

项目功能介绍：https://www.bilibili.com/video/BV18Z4y1r7Ut

## 后台服务端

技术栈：

- [x] springboot微服务框架
- [x] MySQL8数据库
- [x] mybatis-plus
- [x] pagehelper+Mybatis 分页
- [x] hutool 工具
- [x] fastjson 序列化
- [x] Redis 缓存
- [x] shiro 安全框架
- [x] easy-captcha 验证码
- [x] aliyun-oss 对象存储
- [x] kumo 分词

项目结构图：

![9fe677eef3e30908](/typora-assets/9fe677eef3e30908.png)

### Shiro认证和授权

即用户登录并赋予权限

Controller层，用户登录接口（用户名、密码、验证码）

```java
@PostMapping("/toLogin")
public RespBean userLogin(@RequestBody HashMap<String, String> params, HttpSession session) {
    String username = params.get("username");
    String password = params.get("password");
    String verify_code = params.get("verify_code");

    //1、比较验证码
    String session_verify = (String) session.getAttribute("verify_code");
    log.warn("session_verify===>" + session_verify);
    if (StringUtils.hasLength(verify_code) && session_verify.equalsIgnoreCase(verify_code)) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(username, password));

            //从session中获取认证的TUser对象 返回给前端
            TUser user = (TUser) session.getAttribute("USER_SESSION");
            return RespBean.success("ok", user);
        } catch (UnknownAccountException e) {
            throw new LoginException("用户名不存在！");
        } catch (IncorrectCredentialsException e) {
            throw new LoginException("密码错误！");
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    } else {
        throw new LoginException("验证码错误！");
    }
}
```

1、首先比较验证码是否相同

### 全局异常定制

### 文件上传

### 多条件查询

## 后台管理端

### 技术栈

- [x] Vue-Cli3 + vue-router + vuex
- [x] axios 网络请求
- [x] element-ui
- [x] echarts 图表
- [x] hover.css hover样式
- [x] moment.js  时间日期格式化
- [x] nprogress 进度条
- [x] qs 序列化和反序列化（URL查询字符串）
- [x] vue-lazyload 图片懒加载
- [x] vue-particles 粒子特效
- [x] wangeditor 富文本编辑器
- [x] less@4.1.1 + less-loader@6.0.0  写Css样式
- [x] clipboard 粘贴板
- [x] codemirror 在线编辑代码
- [x] screenfull 全屏
- [x] vue-count-to 动态数字
- [x] mavon-editor markdown文本编辑器

### 首页

![image-20221010153538033](/typora-assets/image-20221010153538033.png)

### 游记管理

#### 旅行游记

![image-20221010153607338](/typora-assets/image-20221010153607338.png)

#### 新增游记

![image-20221010153641854](/typora-assets/image-20221010153641854.png)

### 系统管理

#### 用户管理

![image-20221010153654069](/typora-assets/image-20221010153654069.png)

#### 角色&权限

该功能未完成（后续完善...）

![image-20221010153723556](/typora-assets/image-20221010153723556.png)

### 旅行家管理

#### 旅行家列表

![image-20221010153755181](/typora-assets/image-20221010153755181.png)

#### 新增旅行家

![image-20221010153818099](/typora-assets/image-20221010153818099.png)

#### 旅行家文章列表

![image-20221010153835418](/typora-assets/image-20221010153835418.png)

#### 新增旅行家文章

![image-20221010153849703](/typora-assets/image-20221010153849703.png)

### 路线管理

#### 路线列表

![image-20221010153910994](/typora-assets/image-20221010153910994.png)

#### 新增路线

![image-20221010153947856](/typora-assets/image-20221010153947856.png)

#### 旅行社

![image-20221010154001057](/typora-assets/image-20221010154001057.png)

### 组件管理

这部分是elAdmin框架自带的，小伙伴们写代码可以参考规范哦

<table>
    <tr>
        <td>
            <img src="/typora-assets/image-20221010154348910.png"/>
        </td>
        <td>
            <img src="/typora-assets/image-20221010154357952.png"/>
        </td>
    </tr>    
    <tr>
        <td>
            <img src="/typora-assets/image-20221010154407701.png"/>
        </td>
        <td>
            <img src="/typora-assets/image-20221010154415387.png"/>
        </td>
    </tr>    
    <tr>
        <td>
            <img src="/typora-assets/image-20221010154423376.png"/>
        </td>
    </tr>    
</table>
### 关于我

![image-20221010154643361](/typora-assets/image-20221010154643361.png)

## 客户端

### 技术栈

- [x] Vue-Cli3 + vue-router + vuex
- [x] axios 网络请求
- [x] element-ui
- [x] echarts 图表
- [x] hover.css hover样式
- [x] moment.js  时间日期格式化
- [x] nprogress 进度条
- [x] qs 序列化和反序列化（URL查询字符串）
- [x] vue-lazyload 图片懒加载
- [x] vue-particles 粒子特效
- [x] wangeditor 富文本编辑器
- [x] less@4.1.1 + less-loader@6.0.0  写Css样式

### 登录页

![image-20221010152508335](/typora-assets/image-20221010152508335.png)

### 注册页

![image-20221010152525082](/typora-assets/image-20221010152525082.png)

注册登录演示：

![111](/typora-assets/111.gif)

### 首页

![image-20221010152602758](/typora-assets/image-20221010152602758.png)

### 旅行家专栏页

![image-20221010152941051](/typora-assets/image-20221010152941051.png)

### 去旅行页

![image-20221010153000243](/typora-assets/image-20221010153000243.png)

### 购物车页

![image-20221010153014954](/typora-assets/image-20221010153014954.png)

### 关于我页

![image-20221010153033842](/typora-assets/image-20221010153033842.png)
