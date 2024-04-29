package com.nhnacademy.shoppingmall.controller.auth;


import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.CookieUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
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

        Optional<Cookie> optionalCookie=  CookieUtils.getCookie(req,"JSESSIONID");
        Cookie cookie = optionalCookie.get();
        if(Objects.nonNull(cookie)){
            cookie.setValue("");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }

        return "shop/login/login_form";
    }
}
