package com.ifan112.demo.sb.web.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RoutingController {

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        if (null != SecurityUtils.getSubject().getPrincipal()) {
            return "index";
        }

        String e = (String) request.getAttribute("shiroLoginFailure");

        if (e != null) {
            System.out.println("上次登录认证时的错误：");
        }

        return "login";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }


}
