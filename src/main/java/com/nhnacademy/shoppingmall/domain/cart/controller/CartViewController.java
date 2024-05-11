package com.nhnacademy.shoppingmall.domain.cart.controller;

import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.SessionConst;
import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.cart.service.CartService;
import com.nhnacademy.shoppingmall.domain.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/cart.do")
public class CartViewController implements BaseController {
    private CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl(), new ProductRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);
        Integer nonMemberKey = (Integer) session.getAttribute(SessionConst.NON_MEMBER_CART_KEY);

        req.setAttribute("cartViewList", cartService.getCartView(Optional.ofNullable(userId), Optional.ofNullable(nonMemberKey)));
        return "shop/cart/cart";
    }
}
