package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/signup.do")
public class SignupPostController implements BaseController {
    public static final int INIT_USER_POINT = 100000;

    @Override
    public String execute(@Valid HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String password = req.getParameter("user_password");
        String userName = req.getParameter("user_name");
        String birth = req.getParameter("birthdate").replaceAll("-", "");

        UserRepository repository = new UserRepositoryImpl();

        //Error
        if (repository.countByUserId(userId) > 0) {
            req.setAttribute("error", "이미 존재하는 아이디입니다.");
            return "shop/login/login_form";
        }

        User user = new User(userId, userName, password, birth, User.Auth.ROLE_USER, INIT_USER_POINT, LocalDateTime.now(), null);

        //Validation
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        if (!validate.isEmpty()) {
            req.setAttribute("validate", validate);
            return "shop/signup/signup_form";
        }

        repository.save(user);

        return "redirect:/login.do";
    }
}
