package com.nhnacademy.shoppingmall.domain.product.controller.admin;

import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/get.do")
public class ProductAdminDetailViewController implements BaseController{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return "shop/admin/product/product_admin_detail";
    }
}
