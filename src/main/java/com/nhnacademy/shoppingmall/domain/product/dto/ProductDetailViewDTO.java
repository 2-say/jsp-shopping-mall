package com.nhnacademy.shoppingmall.domain.product.dto;

import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductDetailViewDTO {
    private final Product product;
    private final List<String> imageNameList;
}
