package com.nhnacademy.shoppingmall.entity.category.repository;

import com.nhnacademy.shoppingmall.entity.category.domain.Categories;

import java.util.List;

public interface CategoryRepository {
    List<Categories> selectCategory();

    //카테고리 id로 이름 조회
    String findById(int categoryId);

    void createCategory(String categoryName);

    // 카테고리 정보 수정
    void updateCategory(String categoryId, String categoryName, String parentCategoryId);

    // 카테고리 삭제
    void deleteCategory(String categoryId);
}
