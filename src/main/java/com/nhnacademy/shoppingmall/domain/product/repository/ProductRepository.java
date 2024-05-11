package com.nhnacademy.shoppingmall.domain.product.repository;

import com.nhnacademy.shoppingmall.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    public int save(Product product);

    public int delete(Integer productId);

    public Optional<Product> findById(Integer productId);

    public void update(Product product);

    public int findLastNumber();

    public List<Product> findByPageSize(int page, int pageSize);

    public Optional<List<Product>> findByProductAndCategoryLimit(Integer categoryId, int page, int pageSize);

    //카테고리 해당하는 사이즈만큼 상품 가져오기

    public boolean existByProductId(Integer productId);

    void decreaseProductQuantity(Integer productId, int amount);

    int countProductFieldByProductId(Integer productId);
}
