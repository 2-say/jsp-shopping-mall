package com.nhnacademy.shoppingmall.domain.image.repository;

import com.google.common.collect.ArrayListMultimap;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    int save(int productId, String fileName);

    int deleteByProductId(int productId);

    Optional<String> findImageById(Integer productId);

    List<String> findAllImageNameById(Integer productId);

}
