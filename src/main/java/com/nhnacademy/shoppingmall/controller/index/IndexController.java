package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.CookieUtils;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static com.nhnacademy.shoppingmall.common.util.ApplicationConfigConst.MAX_PAGE_SIZE;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = {"/index.do"})
public class IndexController implements BaseController {
    private ProductService service = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //요청 파라미터
        int currPage = req.getParameter("page") != null ? FormValidator.stringToInteger(req.getParameter("page")) : 1;
        String categoryName = req.getParameter("category");

        List<Product> productList = null;

        //카테고리 설정 유무
        if (categoryName != null) {
            productList = service.findByPageSizeAndCategory(categoryName, currPage - 1, MAX_PAGE_SIZE);
        } else {
            productList = service.findByPageSize(currPage - 1, MAX_PAGE_SIZE);
        }

        //쿠키 생성
        if (!CookieUtils.isCookieExists(req, "recentProducts")) {
            CookieUtils.AddObjectCookie(new ArrayList<>(), resp);
        }
        Queue<Product> recentProducts = CookieUtils.getProductQueueFromCookie(req);


        //응답 파라미터
        req.setAttribute("recentProducts", recentProducts);
        req.setAttribute("page", currPage);
        req.setAttribute("pageItem",  new Page<>(productList, productList.size()));

        return "shop/main/index";
    }
}
