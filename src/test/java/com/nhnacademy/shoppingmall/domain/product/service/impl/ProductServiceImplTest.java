package com.nhnacademy.shoppingmall.domain.product.service.impl;

import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {
    ProductService productService = ProductServiceImpl.builder()
            .productCategoryRepository(new ProductCategoryRepositoryImpl())
            .productRepository(new ProductRepositoryImpl())
            .imageRepository(new ImageRepositoryImpl())
            .cartRepository(new CartRepositoryImpl())
            .build();

    @Test
    @DisplayName("카테고리 없을때")
    void findAllByCategory() {

    }

    @Test
    void getProductView() {

    }

    @Test
    void saveProduct() {
    }

    @Test
    void deleteProduct() {
    }
}