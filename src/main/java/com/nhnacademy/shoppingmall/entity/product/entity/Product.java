package com.nhnacademy.shoppingmall.entity.product.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class Product {
    private final Integer id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

