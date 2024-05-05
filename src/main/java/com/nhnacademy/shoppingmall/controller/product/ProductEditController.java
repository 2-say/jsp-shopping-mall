package com.nhnacademy.shoppingmall.controller.product;

import com.google.common.collect.ArrayListMultimap;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.entity.Image;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.product.model.ImageDao;
import com.nhnacademy.shoppingmall.product.repository.ProductRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productEdit.do")
public class ProductEditController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String productId = req.getParameter("productId");
        ProductRepositoryImpl repository = new ProductRepositoryImpl();
        Optional<Product> productOption = repository.findById(productId);
        Product product = productOption.get();

        req.setAttribute("item", product);

        ImageDao dao = new ImageDao();
        ArrayListMultimap<String, String> byId = dao.findById(productId);
        List<String> imageList = byId.get(productId);

        req.setAttribute("imageList", imageList);

        return "shop/admin/product/product_form";
    }


}
