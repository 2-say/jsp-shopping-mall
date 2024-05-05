package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FileUtils;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.product.model.ImageDao;
import com.nhnacademy.shoppingmall.product.repository.ProductRepositoryImpl;
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
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductRepositoryImpl repository = new ProductRepositoryImpl();
        int insertId = repository.findLastNumber();

        Product product = null;
        Map<String, String> param = FileUtils.fileSave(req);
        String product_price = (String) param.get("product_price");
        String productField = (String) param.get("productField");
        String category = (String) param.get("category");

        //parsing Validator
        if (FormValidator.isNumeric(product_price) && FormValidator.isNumeric(productField) && FormValidator.isNumeric(category)) {
            String productName = (String) param.get("product_name");
            int productPrice = Integer.parseInt(product_price);
            int productFieldInt = Integer.parseInt(productField);
            int categoryId = Integer.parseInt(category);
            String description = (String) param.get("description");

            if (param.get("productId") != null) {
                insertId = Integer.parseInt(param.get("productId"));
            }
            product = new Product(insertId, productName, productPrice, description, productFieldInt, LocalDateTime.now(), categoryId);
        } else {
            req.setAttribute("error", "재고와, 가격은 숫자만 입력할 수 있습니다.");
            return "shop/admin/product/product_form";
        }

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
        if (param.get("productId") != null) {
            repository.update(product);
        } else {
            repository.save(product);
        }

        ImageDao imageDao = new ImageDao();
        List<String> fileNames = (List<String>) req.getAttribute("fileNames");
        if (param.get("productId") != null) {
            imageDao.deleteByProductId(insertId);
        }
        for (String fileName : fileNames) {
            imageDao.save(insertId, fileName);
        }
        return "redirect:/admin/index.do";
    }

}
