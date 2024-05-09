package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.cart.service.CartService;
import com.nhnacademy.shoppingmall.entity.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.entity.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.image.service.ImageService;
import com.nhnacademy.shoppingmall.entity.image.service.ImageServiceImpl;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.entity.productcategory.repository.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.productcategory.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.entity.productcategory.service.ProductCategoryServiceImpl;
import com.nhnacademy.shoppingmall.entity.userscart.repository.UserCartRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productDelete.do")
public class ProductDeleteController implements BaseController {

    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());
    private ImageService imageService = new ImageServiceImpl(new ImageRepositoryImpl());
    private CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        Integer productId = FormValidator.stringToInteger(req.getParameter("productId"));

        productCategoryService.deleteProductCategory(productId);
        imageService.deleteProductImage(productId);
        cartService.deleteProductCart(productId);

        //상품 삭제
        productService.deleteProduct(productId);

        //TODO: 쿠키에서 내용 삭제


        return "redirect:/admin/productList.do";
    }
}
