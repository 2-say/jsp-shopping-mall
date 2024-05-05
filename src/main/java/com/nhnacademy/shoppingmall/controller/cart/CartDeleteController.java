package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cartDelete.do")
public class CartDeleteController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String productId = req.getParameter("itemId");

        Product product = new ProductRepositoryImpl().findById(productId).get();

        HttpSession session = req.getSession(false);
        Map<Product, Integer> cartMap = (Map<Product, Integer>) session.getAttribute(SessionConst.CART_MAP);

        cartMap.remove(product);
        session.setAttribute(SessionConst.CART_MAP, cartMap);

        return null;
    }
}
