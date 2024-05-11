package com.nhnacademy.shoppingmall.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 100, message = "상품 이름은 최소 3글자 이상 100자 이하로 입력해주세요!")
    private String name;
    @NotNull
    @Range(min = 0, max = 1_000_000_000, message = "금액은 -가 될 수 없습니다 , 10억 이하 입력")
    private int price;
    private String description;
    @Range(min = 1, max = 9999, message = "상품 개수는 1~9999개 입력 가능합니다")
    private int productField; //상품 재고
    private LocalDateTime rDate;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

