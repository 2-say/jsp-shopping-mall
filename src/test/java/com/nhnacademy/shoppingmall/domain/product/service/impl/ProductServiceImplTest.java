package com.nhnacademy.shoppingmall.domain.product.service.impl;

import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepository;
import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductAddFormDTO;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductDetailViewDTO;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nhnacademy.shoppingmall.global.common.util.ApplicationConfigConst.MAX_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;


@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ImageRepository imageRepository = Mockito.mock(ImageRepository.class);
    ProductCategoryRepository productCategoryRepository = Mockito.mock(ProductCategoryRepository.class);
    CartRepository cartRepository = Mockito.mock(CartRepository.class);
    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);

    List<String> testImageNames = new ArrayList<>() {{
            add("test1.jpg");
            add("test2.jpg");
    }};

    Product testProduct = new Product(1, "testName", 10000, "test", 10, LocalDateTime.now());


    ProductService productService = ProductServiceImpl.builder()
            .productCategoryRepository(productCategoryRepository)
            .productRepository(productRepository)
            .imageRepository(imageRepository)
            .cartRepository(cartRepository)
            .categoryRepository(categoryRepository)
            .build();


    @Test
    @DisplayName("카테고리 설정 X, 디폴트 상품 가져오기")
    void findAllDefaultByCategoryLimitPage() {
        // given
        // when
        productService.findAllByCategoryLimitPage(Optional.empty(), 1);
        // then
        Mockito.verify(productRepository, Mockito.times(1)).findByPageSize(anyInt(), anyInt());
    }

    @Test
    @DisplayName("카테고리 설정시, 카테고리 상품 가져오기")
    void findAllByCategoryLimitPage() {
        // given
        Mockito.when(productRepository.findByProductAndCategoryLimit(anyInt(), anyInt(), anyInt())).thenReturn(Optional.of(new ArrayList<>()));
        // when
        productService.findAllByCategoryLimitPage(Optional.ofNullable(Integer.valueOf(1)), 1);
        // then
        Mockito.verify(productRepository, Mockito.times(1)).findByProductAndCategoryLimit(anyInt(), anyInt(), anyInt());
    }

    @Test
    @DisplayName("조회하는 페이지가 0 이하일 때, 오류 발생")
    void findAllByCategoryLimitPage_down_0() {
        // given
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.findAllByCategoryLimitPage(Optional.ofNullable(1), 0);
        });
    }

    @Test
    @DisplayName("productId null일때 예외 발생")
    void getProductView_productId_is_null() {
        // given
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    productService.getProductView(null);
        });
    }

    @Test
    @DisplayName("존재하지 않는 상품일때 예외 발생")
    void getProductView_product_is_notExist() {
        // given
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        Mockito.when(imageRepository.findAllImageNameById(anyInt())).thenReturn(new ArrayList<>());
        // when
        // then
        Assertions.assertThrows(RuntimeException.class, () -> {
            productService.getProductView(anyInt());
        });
    }

    @Test
    @DisplayName("상품들 가져오기 - 정상 로직")
    void getProductView() {
        // given
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.ofNullable(testProduct));
        Mockito.when(imageRepository.findAllImageNameById(anyInt())).thenReturn(testImageNames);
        // when
        ProductDetailViewDTO detailViewDTO = productService.getProductView(testProduct.getId());
        // then
        Assertions.assertEquals(detailViewDTO.getProduct(), testProduct);
        Assertions.assertEquals(detailViewDTO.getImageNameList(), testImageNames);
    }

    @Test
    @DisplayName("상품 추가 폼 가져오기 - 정상 로직")
    void getProductAddFormView_success() {
        // given
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(testProduct));
        Mockito.when(imageRepository.findAllImageNameById(anyInt())).thenReturn(testImageNames);
        Mockito.when(productCategoryRepository.findByProductId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.findById(anyInt())).thenReturn("test");

        // when
        ProductAddFormDTO addFormDTO = productService.getProductAddFormView(testProduct.getId());

        // then
        Assertions.assertEquals(addFormDTO.getProduct(), testProduct);
        Assertions.assertEquals(addFormDTO.getImageNameList(), testImageNames);
        Assertions.assertNotNull(addFormDTO.getCategoryName());
    }

    @Test
    @DisplayName("상품 추가 폼 가져오기 - 상품 또는 카테고리가 없을 때 예외 발생")
    void getProductAddFormView_exception() {
        // given
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when - then
        Assertions.assertThrows(RuntimeException.class, () -> productService.getProductAddFormView(1));
    }

    @Test
    @DisplayName("상품 저장하기 - 정상 로직")
    void saveProduct_success() {
        // given
        Mockito.when(productRepository.save(any())).thenReturn(1);

        // when
        productService.saveProduct(testProduct, 1, Optional.of(testImageNames));

        // then
        Mockito.verify(productRepository, Mockito.times(1)).save(any());
        Mockito.verify(productCategoryRepository, Mockito.times(1)).save(anyInt(), anyInt());
        Mockito.verify(imageRepository, Mockito.times(2)).save(anyInt(), any());
    }

    @Test
    @DisplayName("상품 업데이트하기 - 정상 로직")
    void updateProduct_success() {
        // given
        Mockito.when(productRepository.existByProductId(anyInt())).thenReturn(true);

        // when
        productService.updateProduct(testProduct, Optional.of(1), Optional.of(testImageNames));

        // then
        Mockito.verify(productRepository, Mockito.times(1)).update(any());
        Mockito.verify(imageRepository, Mockito.times(1)).deleteByProductId(anyInt());
        Mockito.verify(imageRepository, Mockito.times(2)).save(anyInt(), any());
        Mockito.verify(productCategoryRepository, Mockito.times(1)).update(anyInt(), anyInt());
    }

    @Test
    @DisplayName("상품 삭제하기 - 정상 로직")
    void deleteProduct_success() {
        // given
        Mockito.when(productRepository.existByProductId(anyInt())).thenReturn(true);

        // when
        productService.deleteProduct(1);

        // then
        Mockito.verify(productCategoryRepository, Mockito.times(1)).delete(anyInt());
        Mockito.verify(imageRepository, Mockito.times(1)).deleteByProductId(anyInt());
        Mockito.verify(cartRepository, Mockito.times(1)).deleteByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).delete(anyInt());
    }
}