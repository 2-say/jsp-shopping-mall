package com.nhnacademy.shoppingmall.entity.productcategory.service;

import com.nhnacademy.shoppingmall.entity.productcategory.repository.ProductCategoryRepository;

public class ProductCategoryServiceImpl implements ProductCategoryService {

    private ProductCategoryRepository repository;

    public ProductCategoryServiceImpl(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveProductCategory(int productId, int categoryId) {
        if (repository.isExist(productId, categoryId)) {
            throw new IllegalArgumentException("Already exist Ids product Id = " + productId + "category Id = " + categoryId);
        }

        repository.save(productId, categoryId);
    }


    @Override
    public Integer findCategoryIdByProductId(int productId) {
        //TODO: exist 처리
        return repository.findByProductId(productId);
    }
}
