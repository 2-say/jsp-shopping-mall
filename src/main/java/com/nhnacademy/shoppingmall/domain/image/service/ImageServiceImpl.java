package com.nhnacademy.shoppingmall.domain.image.service;

import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepository;

import java.util.Optional;

public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;

    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public String findOneById(Integer productId) {
        Optional<String> imageById = repository.findImageById(productId);
        return imageById.orElse(null);
    }
}
