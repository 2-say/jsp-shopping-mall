package com.nhnacademy.shoppingmall.controller.product;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.product.model.ImageDao;
import com.nhnacademy.shoppingmall.product.repository.ProductRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/get.do")
public class ProductDetailController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        ProductRepositoryImpl repository = new ProductRepositoryImpl();
        Optional<Product> productOptional = repository.findById(req.getParameter("id"));
        Product product = productOptional.get();

        req.setAttribute("item", product);

        ImageDao imageDao = new ImageDao();
        // 이미지 파일 경로 및 ID 파라미터 확인
        ArrayListMultimap<String, String> imageNameMap = imageDao.findById(String.valueOf(product.getId()));

        if (imageNameMap.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        List<String> imageList = imageNameMap.get(String.valueOf(product.getId()));
        req.setAttribute("imageList", imageList);
        return "shop/product/product_detail";
    }
}
