package com.ifan112.demo.springboot.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collections;
import java.util.Map;

public class DemoAuthorizingRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // String username = JWTUtils.getUsername(principals.toString());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("admin");
        authorizationInfo.addStringPermission("test");

        return authorizationInfo;
    }

    private static final Map<String, String> USERS = Collections.singletonMap("abc", "123");

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());

        if (USERS.containsKey(username) && USERS.get(username).equals(password)) {
            User user = new User();
            user.setId(1);
            user.setPassword(password);
            user.setUsername(username);

            return new SimpleAuthenticationInfo(user, password, "");
        }

        return null;
    }

}
