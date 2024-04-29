package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/signup.do")
public class SignupPostController implements BaseController {
    public static final int INIT_USER_POINT = 100000;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String userId = req.getParameter("user_id");
        String password = req.getParameter("user_password");
        String userName = req.getParameter("user_name");
        String birth = req.getParameter("birthdate").replaceAll("-", "");

        UserRepository repository = new UserRepositoryImpl();
        int i = repository.countByUserId(userId);

        if (i > 0) {
            String message = "존재하는 아이디 입니다. 다른 아이디를 입력해주세요";
            req.setAttribute("error", message);
            return "shop/signup/signup_form";
        } else {
            repository.save(new User(userId, userName, password, birth, User.Auth.ROLE_USER, INIT_USER_POINT, LocalDateTime.now(), null));
            return "redirect:/login.do";
        }
    }
}
