package com.xjt.travel.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public Authorizer authorizer(){
        return new ModularRealmAuthorizer();
    }

    //1.创建shiroFilter  负责拦截所有请求
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //配置系统受限资源
        //配置系统公共资源
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/user/toLogin","anon");   //anon 设置为公共资源
        map.put("/user/toLogout","anon");   //anon 设置为公共资源
        map.put("/user/toRegister","anon");   //anon 设置为公共资源
        map.put("/user/getCaptcha","anon");   //anon 设置为公共资源

//        map.put("/route/getByPage","anon");   //测试
//        map.put("/route/all/num","anon");   //测试

        map.put("/**","authc");     //authc 请求该资源需要认证和授权

        //默认登录界面路径（默认的是 /login.jsp）
        shiroFilterFactoryBean.setLoginUrl("/user/toLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    //2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier(value = "customRealm")  Realm customRealm){
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        webSecurityManager.setRealm(customRealm);

        return webSecurityManager;
    }

    //3.创建自定义的realm
    @Bean(name = "customRealm")
    public Realm customRealm(){
        CustomRealm customRealm = new CustomRealm();

        //加盐
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        //开启缓存管理器
//        customRealm.setCachingEnabled(true);
//        customRealm.setAuthorizationCachingEnabled(true);
//        customRealm.setAuthorizationCachingEnabled(true);
//        customRealm.setCacheManager(new EhCacheManager());

        return customRealm;
    }

    /*开启Shiro的注解*/
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    //开启aop注解支持
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier(value = "defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
