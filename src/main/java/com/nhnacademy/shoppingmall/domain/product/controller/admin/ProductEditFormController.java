package com.nhnacademy.shoppingmall.domain.product.controller.admin;

import com.nhnacademy.shoppingmall.domain.product.dto.ProductAddFormDTO;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import com.nhnacademy.shoppingmall.domain.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import com.nhnacademy.shoppingmall.domain.category.repository.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productEdit.do")
public class ProductEditFormController implements BaseController {

    private ProductService productService = ProductServiceImpl.builder()
            .productRepository(new ProductRepositoryImpl())
            .imageRepository(new ImageRepositoryImpl())
            .productCategoryRepository(new ProductCategoryRepositoryImpl())
            .categoryRepository(new CategoryRepositoryImpl())
            .build();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer productId = FormValidator.stringToInteger(req.getParameter("productId"));
        ProductAddFormDTO productAddFormView = productService.getProductAddFormView(productId);
        req.setAttribute("productAddFormView", productAddFormView);
        return "shop/admin/product/product_form";
    }
}
