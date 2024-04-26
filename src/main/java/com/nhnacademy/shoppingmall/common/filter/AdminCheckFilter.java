package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//@WebFilter(urlPatterns = "/*")
@Slf4j
public class AdminCheckFilter extends HttpFilter {

    private static final String[] list = {"/admin/"};

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo11 /admin/ 하위 요청은 관리자 권한의 사용자만 접근할 수 있습니다. ROLE_USER가 접근하면 403 Forbidden 에러처리
        if (isAdminCheckPath(req.getRequestURI())) {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("?");
            if (user.getUserAuth() == User.Auth.ROLE_USER) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        chain.doFilter(req, res);
    }

    private boolean isAdminCheckPath(String requestURI) {
        //todo10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        for (String s : list) {
            if (requestURI.startsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
