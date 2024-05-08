package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productList.do")
public class ProductListController implements BaseController {

    public static final int MAX_PAGE_SIZE = 12;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //요청 파라미터
        int currPage = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
        String category = req.getParameter("category");

        ProductService service = new ProductServiceImpl(new ProductRepositoryImpl());
        List<Product> productList = null;

        //카테고리 설정 유무
        if (category != null) {
            productList = service.findByPageSizeAndCategory(category, currPage - 1, MAX_PAGE_SIZE);
        } else {
            productList = service.findByPageSize(currPage - 1, MAX_PAGE_SIZE);
        }


        //응답 파라미터
        req.setAttribute("page", currPage);
        req.setAttribute("pageItem", new Page<>(productList, productList.size()));

        return "shop/admin/product/product_list";
    }
}
