package com.nhnacademy.shoppingmall.domain.product.controller.admin;

import com.nhnacademy.shoppingmall.domain.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.entity.Product;
import com.nhnacademy.shoppingmall.domain.product.repository.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.domain.product.service.ProductService;
import com.nhnacademy.shoppingmall.domain.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.global.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.global.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.global.common.util.FileUtils;
import com.nhnacademy.shoppingmall.global.common.util.FormValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product.do")
public class ProductAddController implements BaseController {
    private final ProductService productService = ProductServiceImpl.builder()
            .productRepository(new ProductRepositoryImpl())
            .productCategoryRepository(new ProductCategoryRepositoryImpl())
            .imageRepository(new ImageRepositoryImpl())
            .build();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //이미지 저장
        Map<String, String> param = FileUtils.fileSave(req);

        //요청 파라미터
        Integer productPrice = FormValidator.stringToInteger(param.get("product_price"));
        Integer productField = FormValidator.stringToInteger(param.get("productField"));
        Integer categoryId = FormValidator.stringToInteger(param.get("category"));
        String productName = param.get("product_name");
        String description = param.get("description");

        Product product = new Product(null, productName, productPrice, description, productField, LocalDateTime.now());

        //Validation
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Product>> validate = validator.validate(product);

        if (!validate.isEmpty()) {
            req.setAttribute("validate", validate);
            validLogPrint(validate);
            return "shop/admin/product/product_form";
        }

        productService.saveProduct(product, categoryId, Optional.ofNullable((List<String>) req.getAttribute("fileNames")));

        return "redirect:/admin/index.do";
    }

    private void validLogPrint(Set<ConstraintViolation<Product>> validate) {
        for (ConstraintViolation<Product> pv : validate) {
            log.info("pv {}", validate);
            log.info("pv {}", pv.getPropertyPath());
            log.info("pv {}", pv.getMessage());
        }
    }

}
