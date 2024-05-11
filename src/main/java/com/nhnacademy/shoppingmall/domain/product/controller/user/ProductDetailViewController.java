package com.nhnacademy.shoppingmall.domain.product.controller.user;

import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductDetailViewDTO;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import com.nhnacademy.shoppingmall.domain.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Queue;

import static com.nhnacademy.shoppingmall.global.ApplicationConfigConst.MAX_RECENT_VIEW_PRODUCT_SIZE;
import static com.nhnacademy.shoppingmall.global.common.util.FormValidator.stringToInteger;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/get.do")
public class ProductDetailViewController implements BaseController {
    private final ProductService productService = ProductServiceImpl.builder()
            .productRepository(new ProductRepositoryImpl())
            .imageRepository(new ImageRepositoryImpl())
            .build();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer productId = stringToInteger(req.getParameter("productId"));
        log.info("{}", productId);

        ProductDetailViewDTO detailViewDTO = productService.getProductView(productId);

        cookieAddRecentViewProduct(req, resp, detailViewDTO);

        req.setAttribute("detailViewDTO", detailViewDTO);

        return "shop/product/product_detail";
    }

    private static void cookieAddRecentViewProduct(HttpServletRequest req, HttpServletResponse resp, ProductDetailViewDTO detailViewDTO) {
        Queue<Product> recentProducts = CookieUtils.getProductQueueFromCookie(req);

        if (!recentProducts.contains(detailViewDTO.getProduct())) {
            recentProducts.add(detailViewDTO.getProduct());
        }

        while (recentProducts.size() > MAX_RECENT_VIEW_PRODUCT_SIZE) {
            recentProducts.remove();
        }

        CookieUtils.AddObjectCookie(recentProducts, resp);
    }
}
