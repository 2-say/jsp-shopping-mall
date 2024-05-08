package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.entity.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.cart.service.CartService;
import com.nhnacademy.shoppingmall.entity.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.entity.userscart.repository.UserCartRepositoryImpl;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private final CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl());
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String cartKey = (String) se.getSession().getAttribute(SessionConst.NON_MEMBER_CART_KEY);
        Integer cartId = FormValidator.stringToInteger(cartKey);

        //비회원 장바구니 삭제
        cartService.deleteCart(cartId);
        HttpSessionListener.super.sessionDestroyed(se);
    }
}
