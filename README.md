# 星光旅游网

项目功能介绍：https://www.bilibili.com/video/BV18Z4y1r7Ut

## SpringBoot服务端

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

![1646317976186](https://dd-static.jd.com/ddimg/jfs/t1/154124/8/27322/61054/6343c6d6E5c4e5cda/9fe677eef3e30908.png)

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

## 后台管理员

后端样式主要参考了 [element-ui](https://element.eleme.io/#/zh-CN) 和 [EL-ADMIN](https://el-admin.vip/)

技术栈：

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

## 客户端
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

