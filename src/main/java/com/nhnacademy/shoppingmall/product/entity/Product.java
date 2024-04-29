package com.nhnacademy.shoppingmall.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Product {

    enum Category {
        CLOTHES, FURNITURE
    }

    private final String id;
    private final String name;
    private final String price;
    private final String description;
    private final int productField; //상품 재고
    private final LocalDateTime rDate;
    private final String category; //todo 타입 변경 필요
    private final String fileName;
    private final String filePath;
}
