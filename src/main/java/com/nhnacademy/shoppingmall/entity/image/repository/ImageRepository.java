package com.nhnacademy.shoppingmall.entity.image.repository;

import com.google.common.collect.ArrayListMultimap;

public interface ImageRepository {
    int save(int productId, String fileName);

    int deleteByProductId(int productId);

    ArrayListMultimap<Integer, String> findById(int productId);

    String findImageById(int productId);
}
