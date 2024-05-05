package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.product.entity.Product;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/cart.do")
public class CartController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);

        if (session.getAttribute(SessionConst.CART_MAP) == null) {
            session.setAttribute(SessionConst.CART_MAP,new HashMap<>());
        }

        Map<Product, Integer> cartMap = (Map<Product, Integer>) session.getAttribute(SessionConst.CART_MAP);

        req.setAttribute("cartMap", cartMap);

        return "shop/cart/cart";
    }
}
