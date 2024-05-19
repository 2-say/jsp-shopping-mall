package com.nhnacademy.shoppingmall.domain.cart.repository;

import com.nhnacademy.shoppingmall.domain.cart.domain.Cart;

import java.util.List;

public interface CartRepository {
    int saveNotHaveCartId(Cart cart);
    void save(Cart cart);

    void delete(Integer cartId);

    List<Cart> findById(int cartId);

    void updateQuantity(Integer cartId, Integer productId, int newQuantity);

    boolean findDuplicate(int cartId, int productId);

    void deleteByProductId(int productId);
}
