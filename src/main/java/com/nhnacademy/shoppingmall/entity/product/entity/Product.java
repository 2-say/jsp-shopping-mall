package com.nhnacademy.shoppingmall.entity.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Range(min = 0, max = 1_000_000_000)
    private int price;
    private String description;
    @Range(min = 1, max = 9999)
    private int productField; //상품 재고
    private LocalDateTime rDate;
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

