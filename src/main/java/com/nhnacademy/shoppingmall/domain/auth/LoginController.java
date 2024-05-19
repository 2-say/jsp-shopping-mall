package com.nhnacademy.shoppingmall.domain.auth;

import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping(method = RequestMapping.Method.GET, value = "/login.do")
public class LoginController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo13-1 session이 존재하고 로그인이 되어 있다면 redirect:/index.do 반환 합니다.
        try {
            HttpSession session = req.getSession();
            if (session.getAttribute(SessionConst.LOGIN_USER_ID) != null) {
                req.setAttribute(SessionConst.LOGIN_USER_ID,session.getAttribute(SessionConst.LOGIN_USER_ID));
                resp.sendRedirect("redirect:/index.do");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "shop/login/login_form";
    }
}
