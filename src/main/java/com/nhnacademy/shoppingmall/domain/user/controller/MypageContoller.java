package com.nhnacademy.shoppingmall.domain.user.controller;

import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.domain.user.domain.User;
import com.nhnacademy.shoppingmall.domain.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.user.service.UserService;
import com.nhnacademy.shoppingmall.domain.user.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage.do")
public class MypageContoller implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String userId = (String) req.getSession().getAttribute("user_ID");
        User user = userService.getUser(userId);

        req.setAttribute("user",user);
        return "shop/mypage/mypage_form";

    }
}
