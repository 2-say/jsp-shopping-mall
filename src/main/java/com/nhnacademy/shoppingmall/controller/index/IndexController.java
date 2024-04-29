package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.product.model.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RequestMapping(method = RequestMapping.Method.GET, value = {"/index.do"})
public class IndexController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductDao productDao = new ProductDao();
        ArrayList<Product> products = productDao.list();
        Page<Product> page = new Page<>(products, products.size());
        req.setAttribute("page", page);
        return "shop/main/index";
    }
}