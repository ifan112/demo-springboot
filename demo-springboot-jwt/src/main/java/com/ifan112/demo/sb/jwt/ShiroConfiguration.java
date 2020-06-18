package com.ifan112.demo.sb.jwt;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.*;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroConfiguration {

    @Bean
    public AuthorizingRealm authorizingRealm() {
        DemoAuthorizingRealm authorizingRealm = new DemoAuthorizingRealm();
        // authorizingRealm.setcachingen

        // authorizingRealm.setCacheManager(new CacheManager() {
        //     @Override
        //     public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        //         return null;
        //     }
        // });

        return authorizingRealm;
    }

    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("10.10.8.167:6379");
        redisManager.setTimeout(1000);

        return redisManager;
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();

        // redisSessionDAO.setKeyPrefix("SHIRO_JSESSIONID");
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        // redisSessionDAO.setKeySerializer(new RedisSerializer() {
        //     @Override
        //     public byte[] serialize(Object o) throws SerializationException {
        //         return new byte[0];
        //     }
        //
        //     @Override
        //     public Object deserialize(byte[] bytes) throws SerializationException {
        //         return null;
        //     }
        // });

        return redisSessionDAO;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // sessionManager.setDeleteInvalidSessions(true);
        // sessionManager.setSessionDAO(sessionDAO());

        sessionManager.setSessionDAO(sessionDAO());

        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm());
        // securityManager.setCacheManager();

        securityManager.setSessionManager(sessionManager());

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
