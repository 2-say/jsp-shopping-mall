package com.nhnacademy.shoppingmall.view.cart;

import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class CartViewDTO {
    private final Integer cartId;
    private final Product product;

    private final String categoryName;

    private final Integer productQuantity;

}
