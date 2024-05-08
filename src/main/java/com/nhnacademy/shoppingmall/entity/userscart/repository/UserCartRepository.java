package com.nhnacademy.shoppingmall.entity.userscart.repository;

import com.nhnacademy.shoppingmall.entity.userscart.domain.UserCart;

public interface UserCartRepository {
    void save(UserCart userCart);

    UserCart findById(int cartId);

    void update(UserCart userCart);

    void delete(int cartId);

    boolean existsCartByCartId(int cartId);

    boolean existsCartByUserId(String userId);

    Integer findCartIdByUserId(String userId);
}
