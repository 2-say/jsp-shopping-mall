package com.nhnacademy.shoppingmall.domain.cart.service;

import com.nhnacademy.shoppingmall.domain.cart.domain.Cart;
import com.nhnacademy.shoppingmall.domain.cart.dto.CartViewDTO;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface CartService {
    CartViewDTO getCartView(Optional<String> userId, Optional<Integer> nonMemberCartId);

    void saveCart(Integer productId, Integer selectQuantity, Optional<String> userId, Optional<Integer> nonMemberCartId, HttpSession session);

    void deleteCart(Integer cartId);

    void updateCartProductQuantity(Integer cartId, Integer productId, Integer quantity);

}
