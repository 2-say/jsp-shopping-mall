package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product.do")
public class ProductController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return  "shop/admin/product/product_form";
    }
}
