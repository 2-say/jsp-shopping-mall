package com.nhnacademy.shoppingmall.domain.cart.repository;

import com.nhnacademy.shoppingmall.domain.cart.domain.UserCart;

public interface UserCartRepository {
    void save(UserCart userCart);

    UserCart findById(int cartId);

    void delete(Integer cartId);

    boolean isExistsCartByUserId(String userId);

    Integer findCartIdByUserId(String userId);
}
