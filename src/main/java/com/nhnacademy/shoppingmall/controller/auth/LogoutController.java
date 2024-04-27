package com.nhnacademy.shoppingmall.controller.auth;


import com.nhnacademy.shoppingmall.common.util.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;
import java.lang.Object;
import java.util.Optional;

@WebServlet(urlPatterns = "/logout.do")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo13-3 로그아웃 구현
        HttpSession session = req.getSession();

        if (Objects.nonNull(session)) {
            session.invalidate();
        }

        Optional<Cookie> optionalCookie=  CookieUtils.getCookie(req,"JSESSIONID");
        Cookie cookie = optionalCookie.get();
        if(Objects.nonNull(cookie)){
            cookie.setValue("");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
        resp.sendRedirect("/login.do");
    }
}
