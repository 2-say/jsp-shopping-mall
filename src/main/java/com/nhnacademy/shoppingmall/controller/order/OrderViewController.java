package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.SessionConst;
import com.nhnacademy.shoppingmall.entity.cart.domain.Cart;
import com.nhnacademy.shoppingmall.entity.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.cart.service.CartService;
import com.nhnacademy.shoppingmall.entity.cart.service.CartServiceImpl;
import com.nhnacademy.shoppingmall.entity.category.repository.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.category.service.CategoryService;
import com.nhnacademy.shoppingmall.entity.category.service.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.entity.productcategory.repository.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.productcategory.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.entity.productcategory.service.ProductCategoryServiceImpl;
import com.nhnacademy.shoppingmall.entity.userscart.repository.UserCartRepositoryImpl;
import com.nhnacademy.shoppingmall.view.cart.CartViewDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/orderView.do")
public class OrderViewController implements BaseController {
    CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl());
    ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());
    CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);

        List<Cart> cartList = null;

        if(userId != null) {
            cartList = cartService.findByUserId(userId);
        }

        List<CartViewDTO> cartViewList = new ArrayList<>();
        int totalPrice = 0;

        for (Cart cart : cartList) {
            Product product = productService.findById(cart.getProductId());
            Integer categoryId = productCategoryService.findCategoryIdByProductId(product.getId());
            String categoryName = categoryService.findCategoryNameByCategoryId(categoryId);

            CartViewDTO newCartViewDTO = new CartViewDTO(cart.getCartId(), product, categoryName, cart.getCartProductQuantity());
            cartViewList.add(newCartViewDTO);

            totalPrice += product.getPrice() * cart.getCartProductQuantity();
        }

        req.setAttribute("cartViewList", cartViewList);
        req.setAttribute("totalPrice", totalPrice);

        return "shop/order/order_confirm";
    }
}
