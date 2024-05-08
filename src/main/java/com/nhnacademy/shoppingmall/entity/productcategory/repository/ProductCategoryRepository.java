package com.nhnacademy.shoppingmall.entity.productcategory.repository;

public interface ProductCategoryRepository {
    void save(int productId, int categoryId);

    Integer findByProductId(int productId);

    void update(int productId, int newCategoryId);

    void delete(int productId);

    boolean isExist(int productId, int categoryId);
}
