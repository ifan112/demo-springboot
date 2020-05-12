package com.ifan112.demo.springboot.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroConfiguration {

    @Bean
    public AuthorizingRealm authorizingRealm() {
        return new DemoAuthorizingRealm();
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm());

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/index");

        HashMap<String, String> filterChainDefinitionMap = new HashMap<>();
        // login页面允许匿名访问
        filterChainDefinitionMap.put("/login", "anon");
        // 其它页面均需要认证和授权
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilter;
    }


}
