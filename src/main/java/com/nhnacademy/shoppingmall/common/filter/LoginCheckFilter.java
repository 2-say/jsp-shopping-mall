package com.nhnacademy.shoppingmall.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter extends HttpFilter {

    private static final String[] list = {"/mypage/"};
    public static final String LOGIN_USER = "user";


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        if (isLoginCheckPath(req.getRequestURI())) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute(LOGIN_USER) == null) {
                res.sendRedirect("/login.do");
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private boolean isLoginCheckPath(String requestURI) {
        //todo10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        for (String s : list) {
            if (requestURI.startsWith(s)) {
                return true;
            }
        }
        return false;
    }
}