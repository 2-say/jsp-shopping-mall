package com.nhnacademy.shoppingmall.domain.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderDetail {
    private final Integer id;
    private final Integer quantity;
    private final Integer totalPrice;
    private final String address;
    private final String addressee;
    private final String phone;
    private final String comment;
    private final LocalDateTime rDate;
}
