package com.nhnacademy.shoppingmall.product.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Slf4j
public class ImageDao {

    public int save(int productId, String fileName) {
        String sql = "INSERT INTO product_image (product_id, image_name) VALUES(?, ?)";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            pstm.setString(2, fileName);
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteByProductId(int productId) {
        String sql = "DELETE FROM product_image WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            return pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayListMultimap<String, String> findById(String productId) {
        String sql = "SELECT image_name FROM product_image WHERE product_id = ?";

        Connection connection = DbConnectionThreadLocal.getConnection();
        ArrayListMultimap<String, String> imageNameMap = ArrayListMultimap.create();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, productId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                imageNameMap.put(productId, rs.getString("image_name"));
            }

            return imageNameMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
