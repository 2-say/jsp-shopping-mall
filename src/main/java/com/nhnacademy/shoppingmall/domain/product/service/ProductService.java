package com.nhnacademy.shoppingmall.domain.product.service;

import com.nhnacademy.shoppingmall.domain.product.dto.ProductAddFormDTO;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductDetailViewDTO;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductListViewDTO;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllByCategoryLimitPage(Optional<Integer> categoryId, int page);

    ProductDetailViewDTO getProductView(Integer productId);

    List<ProductListViewDTO> getProductListWithCategory(Optional<Integer> categoryId, int page);

    ProductAddFormDTO getProductAddFormView(Integer productId);

    void saveProduct(Product product, Integer categoryId, Optional<List<String>> fileNames);

    void updateProduct(Product product, Optional<Integer> categoryId, Optional<List<String>> fileNames);

    void deleteProduct(Integer productId);

}
