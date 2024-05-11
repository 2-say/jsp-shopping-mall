package com.nhnacademy.shoppingmall.domain.product.dto;

import com.nhnacademy.shoppingmall.domain.category.domain.Category;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductListViewDTO {
    private final Product product;
    private final String categoryName;
}
