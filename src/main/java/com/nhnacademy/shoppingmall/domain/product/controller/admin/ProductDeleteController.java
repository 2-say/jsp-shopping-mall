package com.nhnacademy.shoppingmall.domain.product.controller.admin;

import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import com.nhnacademy.shoppingmall.domain.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productDelete.do")
public class ProductDeleteController  implements BaseController {
    private ProductService productService = ProductServiceImpl.builder()
            .productRepository(new ProductRepositoryImpl())
            .productCategoryRepository(new ProductCategoryRepositoryImpl())
            .productCategoryRepository(new ProductCategoryRepositoryImpl())
            .build();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer productId = FormValidator.stringToInteger(req.getParameter("productId"));

        productService.deleteProduct(productId);

        //TODO: 쿠키에서 해당 상품이 존재할 시 내용 삭제


        return "redirect:/admin/productList.do";
    }
}
