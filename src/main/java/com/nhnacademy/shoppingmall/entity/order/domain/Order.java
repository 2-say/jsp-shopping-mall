package com.nhnacademy.shoppingmall.entity.order.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Order {
    private int id;
    private int userId;
    private int productId;
    private int totalPrice;
    private String comment;
    private LocalDateTime rDate;
    private String address;
    private String addressee;
    private String phone;
}
