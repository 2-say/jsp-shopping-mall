package com.nhnacademy.shoppingmall.entity.productcategory.service;

public interface ProductCategoryService {
    void saveProductCategory(int productId, int categoryId);


    Integer findCategoryIdByProductId(int productId);
}
