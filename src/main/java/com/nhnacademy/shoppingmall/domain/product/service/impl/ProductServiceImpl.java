package com.nhnacademy.shoppingmall.domain.product.service.impl;

import com.nhnacademy.shoppingmall.domain.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.domain.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepository;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductAddFormDTO;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductDetailViewDTO;
import com.nhnacademy.shoppingmall.domain.product.dto.ProductListViewDTO;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nhnacademy.shoppingmall.global.common.util.ApplicationConfigConst.MAX_PAGE_SIZE;

@Slf4j
@Builder
@Getter
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ImageRepository imageRepository;
    private ProductCategoryRepository productCategoryRepository;
    private CartRepository cartRepository;
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> findAllByCategoryLimitPage(Optional<Integer> categoryId, int page) {
        List<Product> productList = null;

        if (page < 1) {
            throw new IllegalArgumentException("Wrong page value ");
        }

        if (categoryId.isEmpty()) {
            productList = productRepository.findByPageSize(page, MAX_PAGE_SIZE);
        } else {
            productList = productRepository.findByProductAndCategoryLimit(categoryId.get(), page, MAX_PAGE_SIZE).get();
        }
        return productList;
    }

    @Override
    public ProductDetailViewDTO getProductView(Integer productId) {
        List<String> imageNames = imageRepository.findAllImageNameById(productId);
        Optional<Product> product = productRepository.findById(productId);

        if (imageNames.isEmpty()) {
            return new ProductDetailViewDTO(product.get(), null);
        }
        return new ProductDetailViewDTO(product.get(), imageNames);
    }

    @Override
    public List<ProductListViewDTO> getProductListWithCategory(Optional<Integer> categoryId, int page) {
        if (page < 1) {
            throw new IllegalArgumentException("Wrong page value ");
        }

        List<Product> productList = categoryId.isEmpty() ?
                productRepository.findByPageSize(page, MAX_PAGE_SIZE) :
                productRepository.findByProductAndCategoryLimit(categoryId.get(), page, MAX_PAGE_SIZE).orElse(new ArrayList<>());

        List<ProductListViewDTO> productListViewDTOS = new ArrayList<>(productList.size());

        for (Product product : productList) {
            Integer categoryIdValue = categoryId.orElseGet(() -> productCategoryRepository.findByProductId(product.getId()));
            String categoryName = categoryRepository.findById(categoryIdValue);
            productListViewDTOS.add(new ProductListViewDTO(product, categoryName));
        }

        return productListViewDTOS;
    }

    @Override
    public ProductAddFormDTO getProductAddFormView(Integer productId) {
        if (productId == null) {
            throw new RuntimeException("Not found product");
        }

        List<String> imageNames = imageRepository.findAllImageNameById(productId);
        Optional<Product> product = productRepository.findById(productId);

        Integer categoryId =  productCategoryRepository.findByProductId(productId);
        String categoryName = categoryRepository.findById(categoryId);

        return new ProductAddFormDTO(product.get(), imageNames, categoryName);
    }

    @Override
    public void saveProduct(Product product, Integer categoryId, Optional<List<String>> fileNames) {
        int insertProductId = productRepository.save(product);
        productCategoryRepository.save(insertProductId, categoryId);

        if (!fileNames.isEmpty()) {
            List<String> fileNameList = fileNames.get();
            for (String s : fileNameList) {
                log.info("fileName ={} ", s);
                imageRepository.save(insertProductId, s);
            }
        }
    }

    @Override
    public void updateProduct(Product product, Optional<Integer> categoryId, Optional<List<String>> fileNames) {
        if (!productRepository.existByProductId(product.getId())) {
            throw new RuntimeException("Not found Product");
        }

        productRepository.update(product);

        if (!fileNames.isEmpty()) {
            imageRepository.deleteByProductId(product.getId());
            for (String s : fileNames.get()) {
                imageRepository.save(product.getId(), s);
            }
        }

        if (!categoryId.isEmpty()) {
            productCategoryRepository.update(product.getId(), categoryId.get());
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        if (productId != null && productRepository.existByProductId(productId)) {
            productCategoryRepository.delete(productId);
            imageRepository.deleteByProductId(productId);
            cartRepository.deleteByProductId(productId);
            productRepository.delete(productId);
        }
    }
}
