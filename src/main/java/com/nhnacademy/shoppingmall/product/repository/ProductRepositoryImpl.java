package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductRepositoryImpl {
    public int save(Product product) {
        String sql = "INSERT INTO product (product_id, product_name, product_price, product_description, product_field, product_rdate, category_id, image_name, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, product.getId());
            pstm.setString(2, product.getName());
            pstm.setInt(3, product.getPrice());
            pstm.setString(4, product.getDescription());
            pstm.setInt(5, product.getProductField());
            pstm.setString(6, product.getRDate().toString());
            pstm.setInt(7, product.getCategory());
            pstm.setString(8, product.getFileName());
            pstm.setString(9, product.getFilePath());
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
