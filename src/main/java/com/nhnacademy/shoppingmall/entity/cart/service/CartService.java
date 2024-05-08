package com.nhnacademy.shoppingmall.entity.cart.service;

import com.nhnacademy.shoppingmall.entity.cart.domain.Cart;

import java.util.List;

public interface CartService {
    //장바구니 id를 반환합니다.
    int saveNonMemberCart(Cart cart);

    List<Cart> findByCartId(int cartId);

    List<Cart> findByUserId(String userId);

    void updateCart(Cart cart);

    void deleteCart(int cartId);

    void updateCartProductQuantity(int cartId, int productId, int quantity);

    boolean findDuplicate(int cartId, int productId);

    void saveUserCart(String userId, Cart cart);

    boolean existsCartByUserId(String userId);

    int findCartIdByUserid(String userId);

}
