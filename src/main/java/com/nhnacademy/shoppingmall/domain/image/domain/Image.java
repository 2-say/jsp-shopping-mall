package com.nhnacademy.shoppingmall.domain.image.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Image {
    private final int id;
    private final int productId;
    private final String imageName;
}
