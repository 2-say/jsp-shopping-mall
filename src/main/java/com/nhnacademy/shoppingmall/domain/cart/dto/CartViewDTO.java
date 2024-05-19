package com.nhnacademy.shoppingmall.domain.cart.dto;

import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CartViewDTO {

    @Getter
    public static class ProductQuantity {
        Product product;
        Integer selectQuantity;

        public ProductQuantity(Product product, Integer selectQuantity) {
            this.product = product;
            this.selectQuantity = selectQuantity;
        }
    }

    private final List<ProductQuantity> products;
    private final Integer cartId;
}


