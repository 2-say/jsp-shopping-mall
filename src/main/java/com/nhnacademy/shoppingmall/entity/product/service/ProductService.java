package com.nhnacademy.shoppingmall.entity.product.service;

import com.nhnacademy.shoppingmall.entity.product.entity.Product;

import java.util.List;

public interface ProductService {

    int saveProduct(Product product);

    List<Product> findByPageSize(int page, int pageSize);

    public List<Product> findByPageSizeAndCategory(String categoryName, int page, int pageSize);

    Product findById(int productId);

    void deleteOneQuantity(int productId);

    void productUpdate(Product product);

    void deleteProduct(int productId);
}
