package com.nhnacademy.shoppingmall.domain.image.repository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository {
    int save(int productId, String fileName);

    void deleteByProductId(int productId);

    Optional<String> findImageById(Integer productId);

    List<String> findAllImageNameById(Integer productId);

}
