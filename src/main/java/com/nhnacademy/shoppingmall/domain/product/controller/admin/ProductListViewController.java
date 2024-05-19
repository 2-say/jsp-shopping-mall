package com.nhnacademy.shoppingmall.domain.product.controller.admin;

import com.nhnacademy.shoppingmall.domain.category.repository.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductListViewDTO;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import com.nhnacademy.shoppingmall.domain.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.page.Page;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productList.do")
public class ProductListViewController implements BaseController {
    private final ProductService productService = ProductServiceImpl.builder()
            .productRepository(new ProductRepositoryImpl())
            .productCategoryRepository(new ProductCategoryRepositoryImpl())
            .categoryRepository(new CategoryRepositoryImpl())
            .build();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //요청 파라미터
        Integer currPage = req.getParameter("page") != null ? FormValidator.stringToInteger(req.getParameter("page")) : 1;
        Optional<Integer> categoryId = Optional.ofNullable(FormValidator.stringToInteger(req.getParameter("categoryId")));

        List<ProductListViewDTO> productListViewDTOS = productService.getProductListWithCategory(categoryId, currPage);

        req.setAttribute("page", currPage);
        req.setAttribute("pageItem", new Page<>(productListViewDTOS, productListViewDTOS.size()));

        return "shop/admin/product/product_list";
    }
}
