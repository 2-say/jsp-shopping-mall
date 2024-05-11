package com.nhnacademy.shoppingmall.domain.image.repository;

import com.google.common.collect.ArrayListMultimap;
import com.nhnacademy.shoppingmall.global.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ImageRepositoryImpl implements ImageRepository {

    @Override
    public int save(int productId, String fileName) {
        String sql = "INSERT INTO product_image (product_id, image_name) VALUES(?, ?)";
        log.info("productId , file {} {}", productId, fileName );
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

    @Override
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

    @Override
    public List<String> findAllImageNameById(Integer productId) {
        String sql = "SELECT image_name FROM product_image WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<String> imageList = new ArrayList<>();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, productId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                imageList.add(rs.getString("image_name"));
            }
            return imageList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> findImageById(Integer productId) {
        String sql = "SELECT image_name FROM product_image WHERE product_id = ?";
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, productId);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return Optional.ofNullable(rs.getString("image_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

}
