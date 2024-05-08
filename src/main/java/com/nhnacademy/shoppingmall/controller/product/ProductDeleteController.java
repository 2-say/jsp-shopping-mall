package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productDelete.do")
public class ProductDeleteController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = -1;
        ProductRepositoryImpl repository = new ProductRepositoryImpl();
        ImageRepositoryImpl dao = new ImageRepositoryImpl();

        if (FormValidator.isNumeric(req.getParameter("productId"))) {
            productId = Integer.parseInt(req.getParameter("productId"));
        }

        dao.deleteByProductId(productId);
        repository.delete(productId);

        return "redirect:/admin/productList.do";
    }
}
