package com.xjt.travel.exception;

import cn.hutool.extra.servlet.ServletUtil;
import com.xjt.travel.utils.RespBean;
import com.xjt.travel.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
     */
    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(HttpServletRequest request, AuthorizationException e)
    {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request))
        {
            return RespBean.error(e.getMessage());
        }
        else
        {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error/unauth");
            return modelAndView;
        }
    }

    /*登录异常*/
    @ExceptionHandler(LoginException.class)
    public Object loginException(HttpServletRequest request, LoginException e)
    {
        log.error(e.getMessage(), e);
        if(ServletUtil.isPostMethod(request)){
            return RespBean.error(e.getMessage());
        }else{
            return new ModelAndView("/error/500");
        }
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespBean requestNotSupportException(HttpRequestMethodNotSupportedException e)
    {
        log.error(e.getMessage(), e);
        return RespBean.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
//    @ExceptionHandler(RuntimeException.class)
//    public RespBean notFount(RuntimeException e)
//    {
//        log.error("运行时异常:", e);
//        return RespBean.error("运行时异常:" + e.getMessage());
//    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public RespBean handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return RespBean.error("服务器错误，请联系管理员");
    }

}
