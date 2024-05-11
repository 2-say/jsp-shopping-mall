package com.nhnacademy.shoppingmall.domain.auth;


import com.nhnacademy.shoppingmall.domain.auth.exception.LoginFailedException;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.CookieUtils;

import javax.servlet.http.*;
import java.util.Objects;
import java.util.Optional;

@RequestMapping(method = RequestMapping.Method.GET, value = "/logout.do")
public class LogoutController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        if (Objects.nonNull(session)) {
            session.invalidate();
        }

        Optional<Cookie> optionalCookie = CookieUtils.getCookie(req, "JSESSIONID");

        if (optionalCookie.isEmpty()) {
            throw new LoginFailedException("로그인 세션을 찾을 수 없습니다.");
        }

        Cookie cookie = optionalCookie.get();
        cookie.setValue("");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        return "shop/login/login_form";
    }
}
