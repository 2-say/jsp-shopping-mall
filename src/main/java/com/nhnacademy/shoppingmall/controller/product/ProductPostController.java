package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FileUtils;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.category.repository.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.category.service.CategoryService;
import com.nhnacademy.shoppingmall.entity.category.service.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.entity.image.service.ImageService;
import com.nhnacademy.shoppingmall.entity.image.service.ImageServiceImpl;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.image.repository.ImageRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.entity.productcategory.repository.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.productcategory.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.entity.productcategory.service.ProductCategoryServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product.do")
public class ProductPostController implements BaseController {

    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());
    private ImageService imageService = new ImageServiceImpl(new ImageRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //이미지 저장
        Map<String, String> param = FileUtils.fileSave(req);

        //요청 파라미터
        int productPrice = FormValidator.stringToInteger(param.get("product_price"));
        int productField = FormValidator.stringToInteger(param.get("productField"));
        int categoryId = FormValidator.stringToInteger(param.get("category"));
        String productName = (String) param.get("product_name");
        String description = (String) param.get("description");

        Product product = new Product(null, productName, productPrice, description, productField, LocalDateTime.now());

        //Validation
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Product>> validate = validator.validate(product);

        if (!validate.isEmpty()) {
            req.setAttribute("validate", validate);
            for (ConstraintViolation<Product> pv : validate) {
                log.info("pv {}", validate);
                log.info("pv {}", pv.getPropertyPath());
                log.info("pv {}", pv.getMessage());
            }
            return "shop/admin/product/product_form";
        }


        //상품 저장
        int insertProductId = productService.saveProduct(product);
        //상품 카테고리 저장
        productCategoryService.saveProductCategory(insertProductId, categoryId);
        //이미지 저장
        List<String> fileNames = (List<String>) req.getAttribute("fileNames");
        for (String fileName : fileNames) {
            imageService.saveImage(insertProductId, fileName);
        }

        return "redirect:/admin/index.do";
    }

}
