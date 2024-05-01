package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/loginAction.do")
public class LoginPostController implements BaseController {

    public static final int SESSION_KEEP_MAX_MINUTE = 60;
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo13-2 로그인 구현, session은 60분동안 유지됩니다.
        String name = req.getParameter("user_id");
        String password = req.getParameter("user_password");
        HttpSession session = req.getSession();

        try {
            User user = userService.doLogin(name, password);

        } catch (UserNotFoundException e) {
            log.info("login Failed checking username, password");
            session.setAttribute(SessionConst.LOGIN_USER_ID, null);
            String message = "로그인 실패 아이디와 비밀번호를 확인해주세요!";
            req.setAttribute("error", message);
            return "shop/login/login_form";
        }

        session.setAttribute(SessionConst.LOGIN_USER_ID, name);
        session.setMaxInactiveInterval(SESSION_KEEP_MAX_MINUTE * 60);
        log.info("session name = {} ", name);

        return "redirect:/index.do";
    }
}
