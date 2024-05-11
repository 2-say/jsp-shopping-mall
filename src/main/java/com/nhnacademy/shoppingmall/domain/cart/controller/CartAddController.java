package com.nhnacademy.shoppingmall.domain.cart.controller;

import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.cart.service.CartService;
import com.nhnacademy.shoppingmall.domain.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.global.common.util.SessionConst;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cartAdd.do")
public class CartAddController implements BaseController {
    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl(), new ProductRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer productId = FormValidator.stringToInteger(req.getParameter("itemId"));
        Integer selectQuantity = FormValidator.stringToInteger(req.getParameter("productField"));

        //세션
        HttpSession session = req.getSession(true);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);
        Integer nonMemberCartId = (Integer) session.getAttribute(SessionConst.NON_MEMBER_CART_KEY);

        cartService.saveCart(productId, selectQuantity, Optional.ofNullable(userId), Optional.ofNullable(nonMemberCartId), session);
        return null;
    }
}
