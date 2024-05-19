package com.nhnacademy.shoppingmall.domain.order.controller;


import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepository;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.domain.order.dto.OrderCompleteViewDTO;
import com.nhnacademy.shoppingmall.domain.order.repository.OrderDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.order.repository.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.order.service.OrderService;
import com.nhnacademy.shoppingmall.domain.order.service.OrderServiceImpl;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.user.service.impl.UserServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequestMapping(method = RequestMapping.Method.GET, value = "/order/history.do")
public class OrderHistoryViewController implements BaseController {
    private final UserCartRepository userCartRepository = new UserCartRepositoryImpl();
    private final OrderService orderService = OrderServiceImpl.builder().
            cartService(new CartServiceImpl(new CartRepositoryImpl(), userCartRepository, new ProductRepositoryImpl()))
            .orderDetailRepository(new OrderDetailRepositoryImpl())
            .orderRepository(new OrderRepositoryImpl())
            .productRepository(new ProductRepositoryImpl())
            .userService(new UserServiceImpl(new UserRepositoryImpl()))
            .userCartRepository(userCartRepository)
            .build();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);

        OrderCompleteViewDTO orderCompleteViewDTO = orderService.getOrder(Optional.ofNullable(userId));
        req.setAttribute("orderCompleteViewDTO",orderCompleteViewDTO);

        return "shop/order/order_history";
    }
}
