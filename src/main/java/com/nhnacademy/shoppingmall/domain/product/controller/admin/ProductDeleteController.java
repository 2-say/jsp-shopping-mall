package com.nhnacademy.shoppingmall.domain.product.controller.admin;

import com.nhnacademy.shoppingmall.domain.product.dto.ProductDetailViewDTO;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import com.nhnacademy.shoppingmall.domain.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.CookieUtils;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Queue;

import static com.nhnacademy.shoppingmall.global.ApplicationConfigConst.MAX_RECENT_VIEW_PRODUCT_SIZE;

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

        cookieDeleteRecentProduct(req, resp, productId);

        return "redirect:/admin/productList.do";
    }

    private static void cookieDeleteRecentProduct(HttpServletRequest req, HttpServletResponse resp, Integer productId) {
        Queue<Product> recentProducts = CookieUtils.getProductQueueFromCookie(req);

        for (Product recentProduct : recentProducts) {
            if(recentProduct.getId() ==  productId) {
                recentProducts.remove(recentProduct);
            }
        }

        CookieUtils.AddObjectCookie(recentProducts, resp);
    }
}
