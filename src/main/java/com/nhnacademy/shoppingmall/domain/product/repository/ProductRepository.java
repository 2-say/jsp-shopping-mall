package com.nhnacademy.shoppingmall.domain.product.repository;

import com.nhnacademy.shoppingmall.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    int save(Product product);

    int delete(Integer productId);

    Optional<Product> findById(Integer productId);

    void update(Product product);

    List<Product> findByPageSize(int page, int pageSize);

    Optional<List<Product>> findByProductAndCategoryLimit(Integer categoryId, int page, int pageSize);

    //카테고리 해당하는 사이즈만큼 상품 가져오기

    boolean existByProductId(Integer productId);

    void decreaseProductQuantity(Integer productId, int amount);

}
