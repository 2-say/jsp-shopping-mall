package com.nhnacademy.shoppingmall.entity.cart.repository;

import com.nhnacademy.shoppingmall.entity.cart.domain.Cart;

import java.util.List;

public interface CartRepository {
    int saveNotHaveCartId(Cart cart);

    void save(Cart cart);

    void update(Cart cart);

    void delete(int cartId);

    boolean existsById(int cartId);

    List<Cart> findById(int cartId);

    void updateQuantity(int cartId, int productId, int newQuantity);

    boolean findDuplicate(int cartId, int productId);
}
