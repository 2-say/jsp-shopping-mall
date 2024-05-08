package com.nhnacademy.shoppingmall.entity.category.service;

import com.nhnacademy.shoppingmall.entity.category.repository.CategoryRepository;

public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public String findCategoryNameByCategoryId(int categoryId) {
        //TODO : exist 처리
        return repository.findById(categoryId);
    }
}
