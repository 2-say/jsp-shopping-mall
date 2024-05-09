package com.nhnacademy.shoppingmall.entity.productcategory.repository;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {
    @Override
    public void save(int productId, int categoryId) {
        String sql = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, categoryId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer findByProductId(int productId) {
        String sql = "SELECT category_id FROM product_category WHERE product_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("category_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(int productId, int newCategoryId) {
        String sql = "UPDATE product_category SET category_id=? WHERE product_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newCategoryId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int productId) {
        String sql = "DELETE FROM product_category WHERE product_id=?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isExist(int productId, int categoryId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM product_category WHERE product_id = ? AND category_id = ?) AS cnt";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            pstm.setInt(2, categoryId);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("cnt") >= 1;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
