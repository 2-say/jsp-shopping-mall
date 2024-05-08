package com.nhnacademy.shoppingmall.entity.product.repository;

import com.nhnacademy.shoppingmall.entity.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    public int save(Product product);

    public int delete(int productId);

    public Optional<Product> findById(int productId);

    public void update(Product product);

    public int findLastNumber();

    public List<Product> findByPageSize(int page, int pageSize);

    public List<Product> findByPageSizeAndCategory(String categoryName, int page, int pageSize);

    public boolean existByProductId(int productId);

    void deleteOneQuantity(int productId);

    int countProductFieldByProductId(int productId);
}
