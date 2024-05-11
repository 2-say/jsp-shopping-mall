package com.nhnacademy.shoppingmall.domain.cart.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCart {
    private final int cartId;
    private final String userId;
}
