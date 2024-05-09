package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.CookieUtils;
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
import java.util.Queue;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/get.do")
public class ProductDetailController implements BaseController {
    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private ImageServiceImpl imageService = new ImageServiceImpl(new ImageRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = FormValidator.stringToInteger(req.getParameter("id"));
        Product product = productService.findById(productId);


        // 이미지 파일 경로 및 ID 파라미터 확인
        List<String> imageList = imageService.findById(productId);

        if (imageList.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        //쿠키 가져와서
        Queue<Product> recentProducts = CookieUtils.getProductQueueFromCookie(req);
        if(recentProducts.size() > 5) {
            recentProducts.remove();
        }
        recentProducts.add(product);
        //업데이트
        CookieUtils.AddObjectCookie(recentProducts, resp);

        req.setAttribute("item", product);
        req.setAttribute("imageList", imageList);

        return "shop/product/product_detail";
    }
}
