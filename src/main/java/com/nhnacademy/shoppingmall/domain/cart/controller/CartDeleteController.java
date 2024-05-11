package com.nhnacademy.shoppingmall.domain.cart.controller;

import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.cart.service.CartService;
import com.nhnacademy.shoppingmall.domain.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cartDelete.do")
public class CartDeleteController implements BaseController {
    private final CartService service = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl(), new ProductRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer cartId = FormValidator.stringToInteger(req.getParameter("cartId"));
        service.deleteCart(cartId);
        return null;
    }
}
