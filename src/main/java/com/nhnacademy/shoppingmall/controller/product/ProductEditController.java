package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.image.service.ImageService;
import com.nhnacademy.shoppingmall.entity.image.service.ImageServiceImpl;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productEdit.do")
public class ProductEditController implements BaseController {

    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private ImageService imageService = new ImageServiceImpl(new ImageRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = FormValidator.stringToInteger(req.getParameter("productId"));

        Product product = productService.findById(productId);
        req.setAttribute("item", product);

        List<String> imageList = imageService.findById(productId);
        req.setAttribute("imageList", imageList);

        return "shop/admin/product/product_form";
    }
}
