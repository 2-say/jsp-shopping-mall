package com.nhnacademy.shoppingmall.domain.product.repository;

public interface ProductCategoryRepository {
    void save(int productId, Integer categoryId);

    Integer findByProductId(int productId);

    void update(int productId, int newCategoryId);

    void delete(int productId);
}
