package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.product.model.ImageDao;
import com.nhnacademy.shoppingmall.product.repository.ProductRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/cartAdd.do")
public class CartAddController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("itemId");
        int selectQuantity = Integer.parseInt(req.getParameter("productField"));

        ProductRepositoryImpl repository = new ProductRepositoryImpl();
        Product product = repository.findById(productId).get();

        HttpSession session = req.getSession(true);

        if (session.getAttribute(SessionConst.CART_MAP) == null) {
            session.setAttribute(SessionConst.CART_MAP,new HashMap<>());
        }

        Map<Product, Integer> cartMap = (Map<Product, Integer>) session.getAttribute(SessionConst.CART_MAP);

        if (cartMap.containsKey(product)) {
            cartMap.put(product, cartMap.get(product) + selectQuantity);
        } else {
            cartMap.put(product, selectQuantity);
        }

        session.setAttribute(SessionConst.CART_MAP, cartMap);
        return null;
    }
}
