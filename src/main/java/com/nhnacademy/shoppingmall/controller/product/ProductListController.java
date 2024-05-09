package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.FormValidator;
import com.nhnacademy.shoppingmall.entity.category.repository.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.category.service.CategoryService;
import com.nhnacademy.shoppingmall.entity.category.service.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.entity.product.entity.Product;
import com.nhnacademy.shoppingmall.entity.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.product.service.ProductService;
import com.nhnacademy.shoppingmall.entity.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.entity.productcategory.repository.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.entity.productcategory.service.ProductCategoryService;
import com.nhnacademy.shoppingmall.entity.productcategory.service.ProductCategoryServiceImpl;
import com.nhnacademy.shoppingmall.view.product.ProductListDTO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.nhnacademy.shoppingmall.common.util.ApplicationConfigConst.MAX_PAGE_SIZE;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/productList.do")
public class ProductListController implements BaseController {
    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private ProductCategoryService productCategoryService = new ProductCategoryServiceImpl(new ProductCategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //요청 파라미터
        int currPage = req.getParameter("page") != null ? FormValidator.stringToInteger(req.getParameter("page")) : 1;
        String category = req.getParameter("category");

        List<Product> productList = null;

        //카테고리 설정 유무
        if (category != null) {
            productList = productService.findByPageSizeAndCategory(category, currPage - 1, MAX_PAGE_SIZE);
        } else {
            productList = productService.findByPageSize(currPage - 1, MAX_PAGE_SIZE);
        }

        //view DTO 담기
        List<ProductListDTO> productListDTOS = new ArrayList<>();

        for (Product product : productList) {
            Integer categoryId = productCategoryService.findCategoryIdByProductId(product.getId());
            String categoryName = categoryService.findCategoryNameByCategoryId(categoryId);
            productListDTOS.add(new ProductListDTO(product, categoryName));
        }

        //응답 파라미터
        req.setAttribute("page", currPage);
        req.setAttribute("pageItem", new Page<>(productListDTOS, productList.size()));
        req.setAttribute("category", category);

        return "shop/admin/product/product_list";
    }
}
