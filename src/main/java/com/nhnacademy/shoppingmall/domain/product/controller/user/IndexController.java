package com.nhnacademy.shoppingmall.domain.product.controller.user;

import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.page.Page;
import com.nhnacademy.shoppingmall.global.common.util.CookieUtils;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = {"/index.do"})
public class IndexController implements BaseController {
    private final ProductService service = ProductServiceImpl.builder()
            .productRepository(new ProductRepositoryImpl())
            .imageRepository(new ImageRepositoryImpl())
            .build();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int currPage = req.getParameter("page") != null ? FormValidator.stringToInteger(req.getParameter("page")) : 1;
        Integer categoryId = req.getParameter("categoryId") != null ? FormValidator.stringToInteger(req.getParameter("categoryId")) : null;

        List<Product> productList = service.findAllByCategoryLimitPage(Optional.ofNullable(categoryId), currPage);

        //쿠키 생성
        Queue<Product> recentProducts = createRecentViewProductCookie(req, resp);

        //응답 파라미터
        req.setAttribute("recentProducts", recentProducts);
        req.setAttribute("page", currPage);
        req.setAttribute("pageItem",  new Page<>(productList, productList.size()));

        return "shop/main/index";
    }

    private static Queue<Product> createRecentViewProductCookie(HttpServletRequest req, HttpServletResponse resp) {
        if (!CookieUtils.isCookieExists(req, "recentProducts")) {
            CookieUtils.AddObjectCookie(new ArrayList<>(), resp);
        }

        return CookieUtils.getProductQueueFromCookie(req);
    }
}
