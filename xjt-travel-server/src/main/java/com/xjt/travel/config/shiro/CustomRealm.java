package com.xjt.travel.config.shiro;

import com.xjt.travel.domain.TUser;
import com.xjt.travel.service.TUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private TUserService tUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("======doAuthorizationInfo授权=======");

        String principal = (String) principals.getPrimaryPrincipal();
        //2、通过用户名查询所有的权限表达式
        Set<String> permissions = tUserService.getPermissionsByUsername(principal);
        log.debug("获得权限===>{}",permissions.iterator().toString());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.debug("======doGetAuthenticationInfo认证=======");

        //从传过来的token获取到的用户名
        String principal = (String) token.getPrincipal();
        //根据用户名从数据库获得TUser对象
        TUser tUser = tUserService.findByUsername(principal);
        log.debug("====认证的tUser===="+tUser);

        if (tUser.getUsername().equals(principal)){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    principal,
                    tUser.getPassword(),
                    new SimpleByteSource(tUser.getSalt()),
                    this.getName());

            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("USER_SESSION",tUser);

            return simpleAuthenticationInfo;
        }

        return null;
    }
}
