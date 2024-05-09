package com.nhnacademy.shoppingmall.entity.image.service;

import com.google.common.collect.ArrayListMultimap;
import com.nhnacademy.shoppingmall.common.util.FileUtils;
import com.nhnacademy.shoppingmall.entity.image.repository.ImageRepository;

import java.util.List;

public class ImageServiceImpl implements ImageService {

    private ImageRepository repository;

    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public String findOneById(int productId) {

        ArrayListMultimap<Integer, String> imageNameMap = repository.findById(productId);

        if (imageNameMap.isEmpty()) {
            return null;
        }
        List<String> imageNames = imageNameMap.get(productId);
        return imageNames.get(0);
    }

    @Override
    public List<String> findById(int productId) {
        ArrayListMultimap<Integer, String> imageNameMap = repository.findById(productId);

        if (imageNameMap.isEmpty()) {
            return null;
        }

        return imageNameMap.get(productId);
    }

    @Override
    public void saveImage(int productId, String fileName) {
        if (!FileUtils.doesFileExist(fileName)) {
            throw new IllegalArgumentException("Not found image file " + fileName);
        }

        repository.save(productId, fileName);
    }

    @Override
    public void updateImage(int productId, String fileName) {
        if (!FileUtils.doesFileExist(fileName)) {
            throw new IllegalArgumentException("Not found image file " + fileName);
        }

        repository.deleteByProductId(productId);
        repository.save(productId, fileName);
    }

    @Override
    public void deleteProductImage(Integer productId) {
        repository.deleteByProductId(productId);
    }

}
