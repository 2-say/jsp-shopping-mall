package com.nhnacademy.shoppingmall.entity.product.service.impl;

import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.product.repository.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public int saveProduct(Product product) {
        if(product.getId() != null && repository.existByProductId(product.getId())) {
            throw new RuntimeException("Already exist product" + product.getId());
        }

        return repository.save(product);
    }

    @Override
    public List<Product> findByPageSize(int page, int pageSize) {
        if(page < 0 && pageSize < 1) {
            throw new IllegalArgumentException("IllegalArgumentException check page, pageSize" + page + " " + pageSize);
        }

        return repository.findByPageSize(page, pageSize);
    }

    @Override
    public List<Product> findByPageSizeAndCategory(String categoryName, int page, int pageSize) {
        //TODO: 카테고리 검증 필요
        return repository.findByPageSizeAndCategory(categoryName, page, pageSize);
    }

    @Override
    public Product findById(int productId) {
        if(!repository.existByProductId(productId)){
            throw new RuntimeException("Not exist product id" + productId);
        }

        return repository.findById(productId).get();
    }

    @Override
    public void deleteOneQuantity(int productId) {
        if(!repository.existByProductId(productId)) {
            throw new RuntimeException("Not exist product id" + productId);
        }

        int leftField = repository.countProductFieldByProductId(productId);

        if(leftField <= 0) {
            throw new RuntimeException("재고가 부족합니다.");
        }

        repository.deleteOneQuantity(productId);
    }


}
