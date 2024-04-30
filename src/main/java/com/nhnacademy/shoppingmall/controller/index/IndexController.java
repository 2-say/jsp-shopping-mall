package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.product.model.ProductDao;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = {"/index.do"})
public class IndexController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductDao productDao = new ProductDao();
        Page<Product> pageItem;
        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;

        String category = req.getParameter("category");
        ArrayList<Product> products;

        if (category != null) {
            products = productDao.listCategory(category, page-1, 12);
        } else {
            products = productDao.list(page-1, 12);
        }

        pageItem = new Page<>(products, products.size());
        log.info("page.size = {}", pageItem.getContent().size());

        req.setAttribute("page", page);
        req.setAttribute("pageItem", pageItem);

        return "shop/main/index";
    }
}
