package com.nhnacademy.shoppingmall.domain.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Order {
    private final Integer productId;
    private final String userId;
    private final Integer orderDetailId;
}
