package com.xjt.travel.controller;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import com.xjt.travel.domain.TUser;
import com.xjt.travel.exception.LoginException;
import com.xjt.travel.service.TUserService;
import com.xjt.travel.utils.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/user")
public class TUserController {
    @Autowired
    private TUserService tUserService;

    @PostMapping("/toLogin")
    public RespBean userLogin(@RequestBody HashMap<String, String> params, HttpSession session) {
        String username = params.get("username");
        String password = params.get("password");
        String verify_code = params.get("verify_code");
        log.warn("username===>" + username);
        log.warn("password===>" + password);
        log.warn("verify_code===>" + verify_code);

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

    @PostMapping("/toRegister")
    public RespBean registerHandler(@RequestBody HashMap<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        return tUserService.addUser(username, password);
    }

    @PostMapping("/toLogout")
    public RespBean logoutHandler() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return RespBean.success("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("error", e.getMessage());
        }
    }

    @GetMapping("/getCaptcha")
    public void get_captcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        String text = specCaptcha.text();
        System.out.println("验证码=========>" + text);
        session.setAttribute("verify_code", text);

        // 输出图片流
        CaptchaUtil.out(specCaptcha, request, response);
    }

    @RequiresPermissions("system:user:*")
    @GetMapping("/queryByCondition")
    public RespBean queryByCondition(
            @RequestParam(value = "current", defaultValue = "1") String current,
            @RequestParam(value = "pageSize", defaultValue = "6") String pageSize,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "status", required = false) String status) {
        return tUserService.queryByCondition(current,pageSize,id, username, nickname, sex, status);
    }

    @RequiresPermissions("system:user:*")
    @GetMapping("/all")
    public RespBean getAll() {
        return tUserService.getAllUser();
    }

    @RequiresPermissions("system:user:add")
    @PostMapping("/insert")
    public RespBean userInsert(@RequestBody TUser tUser) {
        System.out.println(tUser);
        return tUserService.insertOne(tUser);
    }

    @RequiresPermissions("system:user:update")
    @PostMapping("/update")
    public RespBean updateUser(@RequestBody TUser tUser) {
        System.out.println(tUser);
        return tUserService.updateUser(tUser);
    }

    /*更改用户状态*/
    @RequiresPermissions("system:user:update")
    @GetMapping("/update/status")
    public RespBean updateUserStatusById(@RequestParam("id") String id) {
        return tUserService.updateUserStatusById(id);
    }

    /*删除用户*/
    @RequiresPermissions("system:user:delete")
    @GetMapping("/delete")
    public RespBean deleteUserById(@RequestParam("id") String id) {
        return tUserService.deleteUserById(id);
    }
}
