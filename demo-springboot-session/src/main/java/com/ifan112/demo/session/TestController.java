package com.ifan112.demo.session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.session.web.http.SessionRepositoryFilter.SESSION_REPOSITORY_ATTR;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("name", "test");

        Object sessionObj = request.getAttribute(SESSION_REPOSITORY_ATTR + ".CURRENT_SESSION");

        assert session == sessionObj;

        return session.getId();
    }

    @GetMapping("/test2")
    public String test2(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("name");
    }
}
