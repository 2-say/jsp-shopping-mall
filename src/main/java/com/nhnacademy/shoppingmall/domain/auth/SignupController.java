package com.nhnacademy.shoppingmall.domain.auth;

import com.nhnacademy.shoppingmall.domain.user.service.UserService;
import com.nhnacademy.shoppingmall.domain.user.service.impl.UserServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.domain.user.domain.User;
import com.nhnacademy.shoppingmall.domain.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.domain.user.repository.impl.UserRepositoryImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Set;

import static com.nhnacademy.shoppingmall.global.common.util.ApplicationConfigConst.INIT_USER_POINT;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/signup.do")
public class SignupController implements BaseController {
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String password = req.getParameter("user_password");
        String userName = req.getParameter("user_name");
        String birth = req.getParameter("birthdate").replaceAll("-", "");

        User user = new User(userId, userName, password, birth, User.Auth.ROLE_USER, INIT_USER_POINT, LocalDateTime.now(), null);

        //Validation
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        if (!validate.isEmpty()) {
            req.setAttribute("validate", validate);
            return "shop/signup/signup_form";
        }

        userService.saveUser(user);

        return "redirect:/login.do";
    }
}
