package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.image.service.ImageServiceImpl;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/get.do")
public class ProductDetailController implements BaseController {
    public static final String RECENT_PRODUCTS = "recentProducts";
    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private ImageServiceImpl imageService = new ImageServiceImpl(new ImageRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = FormValidator.stringToInteger(req.getParameter("id"));
        Product product = productService.findById(productId);

        req.setAttribute("item", product);

        // 이미지 파일 경로 및 ID 파라미터 확인
        List<String> imageList = imageService.findById(productId);

        if (imageList.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        req.setAttribute("imageList", imageList);

        return "shop/product/product_detail";
    }
}
