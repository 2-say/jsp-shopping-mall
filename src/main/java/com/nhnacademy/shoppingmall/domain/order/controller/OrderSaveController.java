package com.nhnacademy.shoppingmall.domain.order.controller;

import com.nhnacademy.shoppingmall.domain.order.domain.OrderForm;
import com.nhnacademy.shoppingmall.domain.order.service.OrderService;
import com.nhnacademy.shoppingmall.domain.order.service.OrderServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.SessionConst;
import com.nhnacademy.shoppingmall.global.thread.channel.RequestChannel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/order/save.do")
public class OrderSaveController implements BaseController {
    private final OrderService orderService = new OrderServiceImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);

        if (userId == null) {
            req.setAttribute("error", "로그인 후 다시 시도해주세요");
            return "redirect:/index.do";
        }

        OrderForm orderForm = new OrderForm(req.getParameter("address")
                , req.getParameter("addressee")
                , req.getParameter("phone")
                , req.getParameter("comment")
                , LocalDateTime.now());

        //validation
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<OrderForm>> validate = validator.validate(orderForm);

        if (!validate.isEmpty()) {
            session.setAttribute("validate", validate);
            validLogPrint(validate);
            return "redirect:/cart.do";
        }

        RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");


        //TODO: session에 주문 detail 번호 넣어서 최근껏만 넘어가도록 수정
        orderService.saveOrder(Optional.of(userId), orderForm, requestChannel);

        return "redirect:/order/orderView.do";
    }

    private void validLogPrint(Set<ConstraintViolation<OrderForm>> validate) {
        for (ConstraintViolation<OrderForm> pv : validate) {
            log.info("pv {}", validate);
            log.info("pv {}", pv.getPropertyPath());
            log.info("pv {}", pv.getMessage());
        }
    }
}
