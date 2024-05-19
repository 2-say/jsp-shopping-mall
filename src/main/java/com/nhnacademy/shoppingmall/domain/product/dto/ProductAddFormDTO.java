package com.nhnacademy.shoppingmall.domain.product.dto;

import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductAddFormDTO {
    private final Product product;
    private final List<String> imageNameList;
    private final String categoryName;
}
