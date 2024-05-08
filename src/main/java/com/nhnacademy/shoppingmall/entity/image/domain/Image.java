package com.nhnacademy.shoppingmall.entity.image.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Image {
    private final int id;
    private final int productId;
    private final String imageName;
}
