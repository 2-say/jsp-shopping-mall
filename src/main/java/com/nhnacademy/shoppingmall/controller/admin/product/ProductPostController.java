package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepositoryImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product.do")
public class ProductPostController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String productName = req.getParameter("product_name");
        Product product = null;

        //parsing Validator
        if(FormValidator.isNumeric(req.getParameter("product_price"))  && FormValidator.isNumeric(req.getParameter("productField")) && FormValidator.isNumeric(req.getParameter("category"))) {
            int productPrice = Integer.parseInt(req.getParameter("product_price"));
            int productField = Integer.parseInt(req.getParameter("productField"));
            int categoryId = Integer.parseInt(req.getParameter("category"));
            String fileUpload = req.getParameter("fileUpload");
            String description = req.getParameter("description");
            product = new Product(null, productName, productPrice, description, productField, LocalDateTime.now(), categoryId, " ", "");
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

        ProductRepositoryImpl repository = new ProductRepositoryImpl();
        repository.save(product);

        return "redirect:/admin/index.do";

    }
}
