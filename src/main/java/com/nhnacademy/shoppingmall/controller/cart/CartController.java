package com.nhnacademy.shoppingmall.controller.cart;

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
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/cart.do")
public class CartController implements BaseController {
    private CartService cartService = new CartServiceImpl(new CartRepositoryImpl(), new UserCartRepositoryImpl());
    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());
    private CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute(SessionConst.LOGIN_USER_ID);
        int nonMemberKey = (int) session.getAttribute(SessionConst.NON_MEMBER_CART_KEY);

        List<Cart> cartList = null;

        if(userId != null) {
            cartList = cartService.findByUserId(userId);
        } else {
            //비회원 일 때
            cartList = cartService.findByCartId(nonMemberKey);
        }

        List<CartViewDTO> cartViewList = new ArrayList<>();

        for (Cart cart : cartList) {
            Product product = productService.findById(cart.getProductId());
            Integer categoryId = productCategoryService.findCategoryIdByProductId(product.getId());
            String categoryName = categoryService.findCategoryNameByCategoryId(categoryId);

            CartViewDTO newCartViewDTO = new CartViewDTO(cart.getCartId(), product, categoryName, cart.getCartProductQuantity());
            cartViewList.add(newCartViewDTO);
        }
        
        req.setAttribute("cartViewList", cartViewList);

        return "shop/cart/cart";
    }
}
