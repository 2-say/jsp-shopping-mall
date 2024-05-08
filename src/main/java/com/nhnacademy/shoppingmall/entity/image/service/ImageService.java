package com.nhnacademy.shoppingmall.entity.image.service;

import com.google.common.collect.ArrayListMultimap;

import java.util.List;

public interface ImageService {

    String findOneById(int productId);

    List<String> findById(int productId);

    void saveImage(int productId, String fileName);
}
