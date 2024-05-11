package com.nhnacademy.shoppingmall.domain.order.controller;

import com.nhnacademy.shoppingmall.domain.order.dto.OrderCompleteViewDTO;
import com.nhnacademy.shoppingmall.domain.order.service.OrderService;
import com.nhnacademy.shoppingmall.domain.order.service.OrderServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequestMapping(method = RequestMapping.Method.GET, value = "/order/view.do")
public class OrderViewController implements BaseController {
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);

        OrderCompleteViewDTO orderCompleteViewDTO = orderService.getOrder(Optional.ofNullable(userId));
        req.setAttribute("orderCompleteViewDTO", orderCompleteViewDTO);

        return "shop/order/order_confirm";
    }
}
