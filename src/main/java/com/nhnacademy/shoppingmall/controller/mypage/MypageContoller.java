package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage.do")
public class MypageContoller implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return "shop/mypage/mypage_form";
    }
}
