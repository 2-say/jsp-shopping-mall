package com.nhnacademy.shoppingmall.view.product;

import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductListDTO {
    private final Product product;
    private final String categoryName;
}
