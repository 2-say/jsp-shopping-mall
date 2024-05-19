package com.nhnacademy.shoppingmall.domain.order.controller;

import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepository;
import com.nhnacademy.shoppingmall.domain.cart.repository.UserCartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.domain.order.domain.OrderForm;
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

        OrderCompleteViewDTO orderCompleteViewDTO = orderService.saveOrder(Optional.of(userId), orderForm, requestChannel);
        session.setAttribute(SessionConst.RECENT_COMPLETE_ORDER, orderCompleteViewDTO);

        return "redirect:/order/view.do";
    }

    private void validLogPrint(Set<ConstraintViolation<OrderForm>> validate) {
        for (ConstraintViolation<OrderForm> pv : validate) {
            log.info("pv {}", validate);
            log.info("pv {}", pv.getPropertyPath());
            log.info("pv {}", pv.getMessage());
        }
    }
}
