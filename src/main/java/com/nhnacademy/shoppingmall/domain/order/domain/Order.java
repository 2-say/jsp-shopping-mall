package com.nhnacademy.shoppingmall.domain.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Order {
    private final Integer productId;
    private final String userId;
    private final Integer orderDetailId;
}
