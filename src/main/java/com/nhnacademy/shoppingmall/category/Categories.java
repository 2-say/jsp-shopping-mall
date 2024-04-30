package com.nhnacademy.shoppingmall.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Categories {
    private final List<Category> categories;
    private final String parentName;
}
