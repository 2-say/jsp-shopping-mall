package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.cart.domain.Cart;
import com.nhnacademy.shoppingmall.entity.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.cart.service.CartService;
import com.nhnacademy.shoppingmall.entity.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.entity.userscart.repository.UserCartRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cartUpdate.do")
public class CartUpdateController implements BaseController {
    CartService service = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("cart ID {}", req.getParameter("cartId"));
        log.debug("productId  {}", req.getParameter("productId"));
        log.debug("productField {}", req.getParameter("productField"));


        Integer cartId = FormValidator.stringToInteger(req.getParameter("cartId"));
        Integer productId = FormValidator.stringToInteger(req.getParameter("productId"));
        Integer productField = FormValidator.stringToInteger(req.getParameter("productField"));



        service.updateCartProductQuantity(cartId, productId, productField);

        return null;
    }
}
