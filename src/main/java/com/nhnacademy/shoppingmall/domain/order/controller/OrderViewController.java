package com.nhnacademy.shoppingmall.domain.order.controller;

import com.nhnacademy.shoppingmall.domain.order.dto.OrderCompleteViewDTO;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.GET, value = "/order/view.do")
public class OrderViewController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);

        //orderSave 에서 넘어온 주문 내역만 출력
        OrderCompleteViewDTO recentCompleteOrder = (OrderCompleteViewDTO) session.getAttribute(SessionConst.RECENT_COMPLETE_ORDER);
        req.setAttribute("orderCompleteViewDTO", recentCompleteOrder);

        return "shop/order/order_confirm";
    }
}
