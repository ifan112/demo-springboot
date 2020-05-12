package com.ifan112.demo.springboot.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public Object login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "已经登录了";
        } else {
            UsernamePasswordToken loginToken = new UsernamePasswordToken(username, password);

            try {
                subject.login(loginToken);
            } catch (Exception e) {
                System.out.println("登录失败。");
            }

            // return JWTUtils.sign(username, password);
            return "登陆成功";
        }
    }
}
