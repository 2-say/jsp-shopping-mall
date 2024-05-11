package com.nhnacademy.shoppingmall.domain.image.service;

import com.google.common.collect.ArrayListMultimap;
import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepository;
import com.nhnacademy.shoppingmall.global.common.util.FileUtils;

import java.util.List;
import java.util.Optional;

public class ImageServiceImpl implements ImageService {
    private ImageRepository repository;

    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public String findOneById(Integer productId) {
        Optional<String> imageById = repository.findImageById(productId);
        if (imageById.isEmpty()) {
            return null;
        }
        return  imageById.get();
    }

}
