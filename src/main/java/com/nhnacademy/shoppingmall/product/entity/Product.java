package com.nhnacademy.shoppingmall.product.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Product {
    private final int id;
    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    @Range(min = 0, max = 1_000_000_000)
    private final int price;
    private final String description;
    @Range(min = 1, max = 9999)
    private final int productField; //상품 재고
    private final LocalDateTime rDate;
    private final int category; //todo 타입 변경 필요
}
