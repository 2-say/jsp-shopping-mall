package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.entity.Product;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ProductRepositoryImpl {
    public int save(Product product) {
        String sql = "INSERT INTO product (product_id, product_name, product_price, product_description, product_field, product_rdate, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, product.getId());
            pstm.setString(2, product.getName());
            pstm.setInt(3, product.getPrice());
            pstm.setString(4, product.getDescription());
            pstm.setInt(5, product.getProductField());
            pstm.setString(6, product.getRDate().toString());
            pstm.setInt(7, product.getCategory());
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(int productId) {
        String sql = "DELETE FROM product WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Product> findById(String productId) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, productId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Product product = new Product(
                        rs.getInt("product_id")
                        , rs.getString("product_name")
                        , rs.getInt("product_price")
                        , rs.getString("product_description")
                        , rs.getInt("product_field")
                        , LocalDateTime.parse(rs.getString("product_rdate"), formatter)
                        , rs.getInt("category_id"));
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void update(Product product) {
        String sql = "UPDATE product SET product_name = ?, product_price = ?, product_description = ?, product_field = ?, product_rdate = ?, category_id = ? WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, product.getName());
            pstm.setInt(2, product.getPrice());
            pstm.setString(3, product.getDescription());
            pstm.setInt(4, product.getProductField());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = product.getRDate().format(formatter);
            pstm.setString(5, formattedDate);
            pstm.setInt(6, product.getCategory());
            pstm.setInt(7, product.getId());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public int findLastNumber() {
        String sql = "SELECT MAX(product.product_id) + 1 AS next_id FROM product";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
