package com.nhnacademy.shoppingmall.domain.cart.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class Cart {
    private final Integer cartId;
    private final Integer productId;
    private final Integer cartProductQuantity;
    private final LocalDateTime rDate;
}
