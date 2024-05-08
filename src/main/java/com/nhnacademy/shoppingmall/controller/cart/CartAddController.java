package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.cart.domain.Cart;
import com.nhnacademy.shoppingmall.entity.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.cart.service.CartService;
import com.nhnacademy.shoppingmall.entity.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.entity.userscart.repository.UserCartRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;


@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cartAdd.do")
public class CartAddController implements BaseController {
    private CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = FormValidator.stringToInteger(req.getParameter("itemId"));
        int selectQuantity = FormValidator.stringToInteger(req.getParameter("productField"));

        //세션
        HttpSession session = req.getSession(true);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);
        Integer cartId = (Integer) session.getAttribute(SessionConst.NON_MEMBER_CART_KEY);


        //회원 일 때
        if (userId != null) {
            doMemberService(userId, productId, selectQuantity);
        } else {
            doNonMemberService(cartId, productId, selectQuantity, session);
        }

        return null;
    }


    private void doMemberService(String userId, int productId, int selectQuantity) {
        Integer cartId = null;

        if (cartService.existsCartByUserId(userId)) {
            cartId = cartService.findCartIdByUserid(userId);
        }

        Cart newCart = new Cart(cartId, productId, selectQuantity, LocalDateTime.now());
        cartService.saveUserCart(userId, newCart);
    }

    private void doNonMemberService(Integer cartId, int productId, int selectQuantity, HttpSession session) {
        Cart newCart = new Cart(cartId, productId, selectQuantity, LocalDateTime.now());
        int insertCartId = cartService.saveNonMemberCart(newCart);
        session.setAttribute(SessionConst.NON_MEMBER_CART_KEY, insertCartId);
    }


}
