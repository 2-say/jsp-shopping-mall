package com.nhnacademy.shoppingmall.domain.cart.repository;

import com.nhnacademy.shoppingmall.domain.cart.domain.Cart;

import java.util.List;

public interface CartRepository {
    int saveNotHaveCartId(Cart cart);
    void save(Cart cart);

    void delete(int cartId);

    List<Cart> findById(int cartId);

    void updateQuantity(int cartId, int productId, int newQuantity);

    boolean findDuplicate(int cartId, int productId);

    void deleteByProductId(int productId);
}
